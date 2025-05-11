package com.greenearn.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class PasswordResetRequest {

    @NotBlank(message = "Password cannot be empty or null")
    private String password;

    @NotBlank(message = "Confirmation password cannot be empty or null")
    private String confirmPassword;
}
