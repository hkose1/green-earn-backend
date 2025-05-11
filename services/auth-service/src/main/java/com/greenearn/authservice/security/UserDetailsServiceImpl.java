package com.greenearn.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenearn.authservice.constant.Constants;
import com.greenearn.authservice.entity.RoleEntity;
import com.greenearn.authservice.entity.UserEntity;
import com.greenearn.authservice.exception.custom.ApiException;
import com.greenearn.authservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws RuntimeException {

        final UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ApiException("There is no user registered with this email"));

        RoleEntity role = user.getRole();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + role.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                true,
                user.getAccountNonLocked(),
                authorities);
    }
}
