package com.greenearn.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class BottleTransactionRequestDto {
    @NotNull(message = "Customer id cannot be null")
    private UUID customerId;
    @NotNull(message = "Container id cannot be null")
    private UUID containerId;
    private Integer points;
    private Integer numberOfSmallBottles;
    private Integer numberOfMediumBottles;
    private Integer numberOfLargeBottles;
}
