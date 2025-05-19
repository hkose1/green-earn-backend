package com.greenearn.rewardservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilService {

    public static UUID getLoggedInUserId(Authentication authentication) {
        String userId = ((Jwt) authentication.getPrincipal()).getClaimAsString("userId");
        return UUID.fromString(userId);
    }
}
