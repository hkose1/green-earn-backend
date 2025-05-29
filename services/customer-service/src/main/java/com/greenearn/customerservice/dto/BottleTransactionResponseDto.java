package com.greenearn.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.customerservice.enums.BottleTransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class BottleTransactionResponseDto {
    private UUID id;
    private UUID customerId;
    private UUID containerId;
    private LocalDateTime createdAt;
    private BottleTransactionStatus bottleTransactionStatus;
    private Integer numberOfSmallBottles;
    private Integer numberOfMediumBottles;
    private Integer numberOfLargeBottles;
    private Integer earnedPoints;
}
