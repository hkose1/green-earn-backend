package com.ecommerce.authservice.dto;

import com.ecommerce.authservice.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
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
