package com.greenearn.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UpdateCustomerRequestDto {
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Please a valid phone number.")
    private String phoneNumber;
    private String profileImageUrl;
} 