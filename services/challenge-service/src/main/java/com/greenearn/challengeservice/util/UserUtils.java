package com.greenearn.challengeservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserUtils {

    public static UUID getCurrentUserId(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("userId");
        return UUID.fromString(userId);
    }

    public static String getCurrentUserToken(Authentication authentication) {
        return  ((Jwt) authentication.getPrincipal()).getTokenValue();
    }
}
