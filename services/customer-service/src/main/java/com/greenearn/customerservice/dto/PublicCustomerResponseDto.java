package com.greenearn.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class PublicCustomerResponseDto {
    private Integer totalPoints;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
}
