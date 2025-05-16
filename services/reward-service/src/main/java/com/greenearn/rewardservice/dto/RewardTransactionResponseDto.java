package com.greenearn.rewardservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import com.greenearn.rewardservice.enums.RewardCategory;
import com.greenearn.rewardservice.enums.RewardTransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class RewardTransactionResponseDto {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID customerId;
    private RewardCategory rewardCategory;
    private RewardTransactionStatus rewardTransactionStatus;
    private BrandType brandType;
    private Integer quantity;
    private Integer totalCostPoint;
}
