package com.greenearn.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.customerservice.enums.BottleTransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bottle_transactions")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BottleTransactionEntity extends Auditable {
    private UUID customerId;
    private UUID containerId;
    @Enumerated(EnumType.STRING)
    private BottleTransactionStatus bottleTransactionStatus;
    private Integer numberOfSmallBottles;
    private Integer numberOfMediumBottles;
    private Integer numberOfLargeBottles;
    private Integer earnedPoints;
}
