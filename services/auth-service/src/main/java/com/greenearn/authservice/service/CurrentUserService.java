package com.greenearn.authservice.service;

import com.greenearn.authservice.dto.UserDto;
import com.greenearn.authservice.entity.UserEntity;
import com.greenearn.authservice.mapper.UserMapper;
import com.greenearn.authservice.repository.UserRepository;
import com.greenearn.authservice.security.JwtTokenProvider;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRequestExtractor jwtRequestExtractor;
    private final HttpServletRequest httpServletRequest;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UUID getCurrentUserId() {
        String token = jwtRequestExtractor.extractToken(httpServletRequest);
        if (token == null) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        return jwtTokenProvider.extractUserId(token);
    }

    public UserDto getCurrentUserDto() {
        final UUID userId = getCurrentUserId();
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public UserEntity getCurrentUserEntity() {
        final UUID userId = getCurrentUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}
