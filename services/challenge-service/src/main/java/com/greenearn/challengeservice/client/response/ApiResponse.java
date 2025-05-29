package com.greenearn.challengeservice.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record ApiResponse<T>(String time,
                             Integer code,
                             String path,
                             HttpStatus status,
                             String message,
                             String exception,
                             T data) {
}
