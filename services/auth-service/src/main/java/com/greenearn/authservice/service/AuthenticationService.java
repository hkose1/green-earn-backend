package com.greenearn.authservice.service;

import com.greenearn.authservice.dto.AuthRequest;
import com.greenearn.authservice.dto.AuthResponse;
import com.greenearn.authservice.dto.PasswordResetRequest;
import com.greenearn.authservice.dto.SignupRequest;

public interface AuthenticationService {

    void registerUser(SignupRequest signupRequest);

    AuthResponse login(AuthRequest authRequest);

    void verifyAccountKey(String key);

    void verifyAccountCode(String code);

    void resetPasswordRequest(String email);

    void resetPassword(String key, PasswordResetRequest passwordResetRequest);

}
