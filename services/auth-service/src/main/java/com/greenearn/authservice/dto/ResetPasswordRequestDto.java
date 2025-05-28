package com.greenearn.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResetPasswordRequestDto {
    @NotEmpty(message = "email can not be null or empty")
    private String email;
}
