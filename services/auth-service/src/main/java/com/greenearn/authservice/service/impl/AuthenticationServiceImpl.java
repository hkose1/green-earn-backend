package com.greenearn.authservice.service.impl;

import com.greenearn.authservice.client.CustomerServiceClient;
import com.greenearn.authservice.client.request.CreateCustomerRequestDto;
import com.greenearn.authservice.dto.*;
import com.greenearn.authservice.mapper.UserMapper;
import com.greenearn.authservice.service.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenearn.authservice.entity.CodeConfirmationEntity;
import com.greenearn.authservice.entity.ConfirmationEntity;
import com.greenearn.authservice.entity.RoleEntity;
import com.greenearn.authservice.entity.UserEntity;
import com.greenearn.authservice.enums.EventType;
import com.greenearn.authservice.enums.Role;
import com.greenearn.authservice.event.UserEvent;
import com.greenearn.authservice.exception.custom.ApiException;
import com.greenearn.authservice.exception.custom.BadRequestException;
import com.greenearn.authservice.exception.custom.UserNotVerifiedException;
import com.greenearn.authservice.repository.CodeConfirmationRepository;
import com.greenearn.authservice.repository.ConfirmationRepository;
import com.greenearn.authservice.repository.RoleRepository;
import com.greenearn.authservice.repository.UserRepository;
import com.greenearn.authservice.security.JwtTokenProvider;
import com.greenearn.authservice.security.UserDetailsImpl;
import com.greenearn.authservice.service.AuthenticationService;
import com.greenearn.authservice.util.UserUtils;

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
    private final CurrentUserService currentUserService;


    @Override
    @Transactional
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
                        EventType.CREATE_CUSTOMER,
                        Map.of()
                )
        );
        eventPublisher.publishEvent(
                new UserEvent(
                        userEntity,
                        EventType.REGISTRATION,
                        Map.of("code", codeConfirmationEntity.getCode(), "key", confirmationEntity.getKey())
                )
        );
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserDto updateUserDto) {
        UserEntity currentUser = currentUserService.getCurrentUserEntity();
        currentUser.setFirstname(updateUserDto.getFirstName());
        currentUser.setLastname(updateUserDto.getLastName());
        eventPublisher.publishEvent(
                new UserEvent(
                        currentUser,
                        EventType.UPDATE_CUSTOMER,
                        Map.of()
                )
        );
        userRepository.save(currentUser);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ApiException("No account found for this email"));
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            ));

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            final String jwtToken = jwtService.generateToken(userDetails);
            return AuthResponse.builder().token(jwtToken).build();

        } catch (DisabledException e) {
            throw new UserNotVerifiedException();
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid email or password");
        } catch (Exception e) {
            throw new ApiException("Authentication failed");
        }
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
        Optional<CodeConfirmationEntity> existCodeConfirmationEntity = codeConfirmationRepository.findByUserId(user.getId());
        if (existCodeConfirmationEntity.isPresent()) {
            codeConfirmationRepository.deleteByUserId(user.getId());
        }

        CodeConfirmationEntity codeConfirmationEntity = new CodeConfirmationEntity(user);
        codeConfirmationRepository.save(codeConfirmationEntity);
        eventPublisher.publishEvent(
                new UserEvent(
                        user,
                        EventType.RESET_PASSWORD,
                        Map.of("code", codeConfirmationEntity.getCode())
                )
        );
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
