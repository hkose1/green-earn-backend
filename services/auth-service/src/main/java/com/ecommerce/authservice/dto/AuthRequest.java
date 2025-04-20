package com.ecommerce.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class AuthRequest {

    @NotBlank(message = "Email cannot be empty or null")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty or null")
    private String password;
}
