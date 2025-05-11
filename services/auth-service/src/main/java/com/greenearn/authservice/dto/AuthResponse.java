package com.greenearn.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.authservice.enums.Role;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private String email;
    private Role role;
}
