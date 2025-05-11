package com.greenearn.authservice.exception.custom;

public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException() {
        super("User is not verified. Please check your your email address and try again.");
    }
    public UserNotVerifiedException(String message) {
        super(message);
    }
    public UserNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
