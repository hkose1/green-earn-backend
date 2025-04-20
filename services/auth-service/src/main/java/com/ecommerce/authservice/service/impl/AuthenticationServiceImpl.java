package com.ecommerce.authservice.service.impl;

import com.ecommerce.authservice.dto.AuthRequest;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.PasswordResetRequest;
import com.ecommerce.authservice.dto.SignupRequest;
import com.ecommerce.authservice.entity.CodeConfirmationEntity;
import com.ecommerce.authservice.entity.ConfirmationEntity;
import com.ecommerce.authservice.entity.RoleEntity;
import com.ecommerce.authservice.entity.UserEntity;
import com.ecommerce.authservice.enums.EventType;
import com.ecommerce.authservice.enums.Role;
import com.ecommerce.authservice.event.UserEvent;
import com.ecommerce.authservice.exception.custom.ApiException;
import com.ecommerce.authservice.exception.custom.BadRequestException;
import com.ecommerce.authservice.exception.custom.UserNotVerifiedException;
import com.ecommerce.authservice.repository.CodeConfirmationRepository;
import com.ecommerce.authservice.repository.ConfirmationRepository;
import com.ecommerce.authservice.repository.RoleRepository;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.security.JwtTokenProvider;
import com.ecommerce.authservice.security.UserDetailsImpl;
import com.ecommerce.authservice.service.AuthenticationService;
import com.ecommerce.authservice.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationRepository confirmationRepository;
    private final CodeConfirmationRepository codeConfirmationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void registerUser(SignupRequest signupRequest) {

        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        UserEntity userEntity = userRepository.save(this.buildUser(signupRequest));
        ConfirmationEntity confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        CodeConfirmationEntity codeConfirmationEntity = new CodeConfirmationEntity(userEntity);
        codeConfirmationRepository.save(codeConfirmationEntity);
        eventPublisher.publishEvent(
                new UserEvent(
                        userEntity,
                        EventType.REGISTRATION,
                        Map.of("code", codeConfirmationEntity.getCode(), "key", confirmationEntity.getKey())
                )
        );
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            ));
        } catch (Exception e) {
            if (e instanceof DisabledException subEx) {
                throw new UserNotVerifiedException();
            }
        }

        final UserEntity user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ApiException("No account found for this email"));
        if (!user.getEnabled()) {
            throw new UserNotVerifiedException();
        }

        final String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));
        return AuthResponse.builder().token(jwtToken).build();
    }


    @Override
    public void verifyAccountKey(String key) {
        var confirmationEntity = getUserConfirmation(key);
        var userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void verifyAccountCode(String code) {
        var confirmationEntity = getUserCodeConfirmation(code);
        var userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        codeConfirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void resetPasswordRequest(String email) {
        var user = getUserEntityByEmail(email);
        Optional<ConfirmationEntity> existsConfirmationEntity = confirmationRepository.findByUserId(user.getId());
        if (existsConfirmationEntity.isPresent()) {
            confirmationRepository.deleteByUserId(user.getId());
        }

        ConfirmationEntity confirmationEntity = new ConfirmationEntity(user);
        confirmationRepository.save(confirmationEntity);
        // todo: add event publisher (send mail to the user for resetting the password)
    }

    @Override
    public void resetPassword(String key, PasswordResetRequest passwordResetRequest) {
        var confirmationEntity = getUserConfirmation(key);
        if (!UserUtils.checkPasswordsAreEqual(passwordResetRequest.getPassword(), passwordResetRequest.getConfirmPassword())) {
            throw new ApiException("Passwords do not match");
        }
        UserEntity user = userRepository.findByEmail(confirmationEntity.getUserEntity().getEmail())
                        .orElseThrow(() -> new ApiException("No account found for this email"));
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);
        confirmationRepository.delete(confirmationEntity);
    }


    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByKey(key)
                .orElseThrow(() -> new ApiException("Confirmation key not found"));
    }

    private CodeConfirmationEntity getUserCodeConfirmation(String code) {
        return codeConfirmationRepository.findByCode(code)
                .orElseThrow(() -> new ApiException("Confirmation code not found"));
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));
    }


    private UserEntity buildUser(SignupRequest signupRequest) {
        RoleEntity role = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found"));
        return UserEntity.builder()
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .firstname(signupRequest.getFirstName())
                .lastname(signupRequest.getLastName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(role)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(false)
                .build();
    }
}
