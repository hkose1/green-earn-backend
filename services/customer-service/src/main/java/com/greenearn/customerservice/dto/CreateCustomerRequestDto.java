package com.greenearn.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class CreateCustomerRequestDto {
    @NotNull(message = "User id can not be null")
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
}
