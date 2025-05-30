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

    @NotNull(message = "Container id cannot be null")
    private UUID containerId;
    @NotNull(message = "QR code id cannot be null")
    private UUID qrCodeId;
    private Integer points;
    private Integer numberOfSmallBottles;
    private Integer numberOfMediumBottles;
    private Integer numberOfLargeBottles;
}
