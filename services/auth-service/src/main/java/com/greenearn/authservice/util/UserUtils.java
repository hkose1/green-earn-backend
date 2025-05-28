package com.greenearn.authservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.greenearn.authservice.exception.custom.ApiException;
import com.greenearn.authservice.security.UserDetailsImpl;

import java.util.Objects;
import java.util.UUID;


public final class UserUtils {

    private UserUtils() {}

    private static boolean validateString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean checkPasswordsAreEqual(final String password, final String confirmPassword) {
        return Objects.equals(password, confirmPassword);
    }

    public static UUID getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new ApiException("Could not get current user id");
            }

            Object principal = authentication.getPrincipal();

            return ((UserDetailsImpl) principal).getUser().getId();

        } catch (Exception e) {
            throw new ApiException("Could not get current user id");
        }
    }


}
