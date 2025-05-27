package com.greenearn.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UpdateCustomerRequestDto {
    private String phoneNumber;
    private String profileImageUrl;
} 