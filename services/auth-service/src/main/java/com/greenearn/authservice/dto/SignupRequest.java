package com.greenearn.authservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class SignupRequest {

    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "First name cannot be empty or null")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty or null")
    private String lastName;

    @NotBlank(message = "Email cannot be empty or null")
    @Size(max = 50)
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 6, max = 40, message = "Password length should be between 6 and 40 characters.")
    private String password;

    @Size(min = 6, max = 40, message = "Password length should be between 6 and 40 characters.")
    @Size(min = 6, max = 40)
    private String confirmPassword;


}
