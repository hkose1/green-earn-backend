package com.ecommerce.authservice.util;

import com.ecommerce.authservice.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RequestUtils {

    public static <T> ApiResponse<T> getApiResponse(HttpServletRequest request,
                                                    T data,
                                                    String message,
                                                    HttpStatus status) {

        return new ApiResponse<>(
                LocalDateTime.now().toString(),
                status.value(),
                request.getRequestURI(),
                HttpStatus.valueOf(status.value()),
                message,
                "",
                data
        );

    }

    public static ApiResponse<Void> getApiErrorResponse(Exception e, HttpStatus status) {
        return new ApiResponse<>(
                LocalDateTime.now().toString(),
                status.value(),
                null,
                status,
                "Error Occurred",
                e.getMessage(),
                null
        );
    }
}
