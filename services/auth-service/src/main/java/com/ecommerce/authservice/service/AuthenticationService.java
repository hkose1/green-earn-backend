package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.AuthRequest;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.PasswordResetRequest;
import com.ecommerce.authservice.dto.SignupRequest;

public interface AuthenticationService {

    void registerUser(SignupRequest signupRequest);

    AuthResponse login(AuthRequest authRequest);

    void verifyAccountKey(String key);

    void verifyAccountCode(String code);

    void resetPasswordRequest(String email);

    void resetPassword(String key, PasswordResetRequest passwordResetRequest);

}
