package com.greenearn.authservice.service;

import com.greenearn.authservice.dto.*;

public interface AuthenticationService {

    void registerUser(SignupRequest signupRequest);

    void updateUser(UpdateUserDto updateUserDto);

    AuthResponse login(AuthRequest authRequest);

    void verifyAccountKey(String key);

    void verifyAccountCode(String code);

    void resetPasswordRequest(String email);

    void resetPassword(String code, PasswordResetRequest passwordResetRequest);

}
