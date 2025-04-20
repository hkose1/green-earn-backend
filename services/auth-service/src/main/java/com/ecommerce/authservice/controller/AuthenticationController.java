package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.dto.*;
import com.ecommerce.authservice.service.AuthenticationService;
import com.ecommerce.authservice.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> saveUser(@Valid @RequestBody SignupRequest signupRequest,
                                                      HttpServletRequest request) {
        authenticationService.registerUser(signupRequest);
        return ResponseEntity
                .created(getUri()).body(
                        RequestUtils.getApiResponse(
                                request,
                                null,
                                "Account created. Please check your email to enable your account",
                                HttpStatus.CREATED)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest,
                                                           HttpServletRequest request) {

        AuthResponse authenticationResponseDto = authenticationService.login(authRequest);
        return ResponseEntity
                .ok().body(
                RequestUtils.getApiResponse(
                        request,
                        authenticationResponseDto,
                        "",
                        HttpStatus.OK)
        );
    }

    @GetMapping("/verify/account")
    public ResponseEntity<ApiResponse<Void>> verifyAccount(@RequestParam("key") String key,
                                                           HttpServletRequest request) {
        authenticationService.verifyAccountKey(key);
        return ResponseEntity.ok().body(RequestUtils.getApiResponse(request, null, "Account verified", HttpStatus.OK));
    }

    @GetMapping("/verify-code/account")
    public ResponseEntity<ApiResponse<Void>> verifyCodeAccount(@RequestParam("code") String code,
                                                           HttpServletRequest request) {
        authenticationService.verifyAccountCode(code);
        return ResponseEntity.ok().body(RequestUtils.getApiResponse(request, null, "Account verified", HttpStatus.OK));
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<ApiResponse<Void>> resetPasswordRequest(@RequestBody String email,
                                                                  HttpServletRequest request) {

        this.authenticationService.resetPasswordRequest(email);
        return ResponseEntity.ok().body(RequestUtils.getApiResponse(request, null, "Reset password request has been sent to the " + email, HttpStatus.OK));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam("key") String key,
                                                           @RequestBody PasswordResetRequest passwordResetRequest,
                                                           HttpServletRequest request) {
        authenticationService.resetPassword(key, passwordResetRequest);
        return ResponseEntity.ok().body(RequestUtils.getApiResponse(request, null, "Password updated", HttpStatus.OK));
    }

    private URI getUri() {
        return URI.create("");
    }

}
