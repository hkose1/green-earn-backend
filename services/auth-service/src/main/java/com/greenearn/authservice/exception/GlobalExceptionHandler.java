package com.greenearn.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.greenearn.authservice.dto.ApiResponse;
import com.greenearn.authservice.exception.custom.ApiException;
import com.greenearn.authservice.exception.custom.BadRequestException;
import com.greenearn.authservice.exception.custom.UserNotVerifiedException;
import com.greenearn.authservice.exception.logging.ErrorExceptionLogger;
import com.greenearn.authservice.exception.logging.ExceptionLogger;
import com.greenearn.authservice.exception.logging.WarnExceptionLogger;
import com.greenearn.authservice.util.RequestUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException e, WebRequest request) {
        logException(new WarnExceptionLogger(e, request));

        return buildAndGetApiErrorResponse(e, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {UserNotVerifiedException.class})
    public ResponseEntity<ApiResponse<Void>> handleUserNotVerifiedException(UserNotVerifiedException e, WebRequest request) {
        logException(new ErrorExceptionLogger(e, request));

        return buildAndGetApiErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(BadRequestException e, WebRequest request) {
        logException(new ErrorExceptionLogger(e, request));

        return buildAndGetApiErrorResponse(e, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e, WebRequest request) {
        logException(new ErrorExceptionLogger(e, request));

        return buildAndGetApiErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void logException(ExceptionLogger exceptionLogger) {
        if (exceptionLogger == null) return;
        exceptionLogger.logException();
    }

    private static ResponseEntity<ApiResponse<Void>> buildAndGetApiErrorResponse(Exception e, HttpStatus httpStatus) {

        final ApiResponse<Void> apiErrorResponse = RequestUtils.getApiErrorResponse(e, httpStatus);

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

}
