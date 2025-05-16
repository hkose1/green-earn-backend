package com.greenearn.authservice.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UpdateCustomerRequestDto {
    private UUID userId;
    private String firstName;
    private String lastName;
}
