package com.ecommerce.authservice.util;

import com.ecommerce.authservice.exception.custom.ApiException;
import com.ecommerce.authservice.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;


public final class UserUtils {

    private UserUtils() {}

    private static boolean validateString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean checkPasswordsAreEqual(final String password, final String confirmPassword) {
        if (password == null || confirmPassword == null) return false;
        if (validateString(password) || validateString(confirmPassword)) return false;
        return password.equals(confirmPassword);
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
