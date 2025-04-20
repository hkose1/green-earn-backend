package com.ecommerce.authservice.exception.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}