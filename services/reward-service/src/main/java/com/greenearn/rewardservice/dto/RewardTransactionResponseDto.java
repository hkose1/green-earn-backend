package com.greenearn.rewardservice.dto;

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
@Builder
public class RewardTransactionResponseDto {
    private UUID id;
    private String rewardTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
    private String failureMessage;
    private RewardCategory rewardCategory;
    private RewardTransactionStatus rewardTransactionStatus;
    private BrandType brandType;
    private Integer quantity;
    private Integer totalCostPoint;
}
