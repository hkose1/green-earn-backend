package com.greenearn.rewardservice.mapper;

import com.greenearn.rewardservice.dto.CreateRewardTransactionRequestDto;
import com.greenearn.rewardservice.dto.RewardTransactionResponseDto;
import com.greenearn.rewardservice.entity.RewardTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardTransactionMapper {

    public static RewardTransactionResponseDto map2ResponseDto(RewardTransactionEntity rewardTransaction) {
        return RewardTransactionResponseDto.builder()
                .id(rewardTransaction.getId())
                .rewardTitle(rewardTransaction.getRewardTitle())
                .rewardTransactionStatus(rewardTransaction.getRewardTransactionStatus())
                .rewardCategory(rewardTransaction.getRewardCategory())
                .createdAt(rewardTransaction.getCreatedAt())
                .updatedAt(rewardTransaction.getUpdatedAt())
                .brandType(rewardTransaction.getBrandType())
                .userId(rewardTransaction.getUserId())
                .failureMessage(rewardTransaction.getFailureMessage())
                .totalCostPoint(rewardTransaction.getTotalCostPoint())
                .quantity(rewardTransaction.getQuantity())
                .build();
    }

    public static RewardTransactionEntity mapCreateRewardTransactionRequest2Entity(CreateRewardTransactionRequestDto createRewardTransactionRequestDto) {
        return RewardTransactionEntity.builder()
                .brandType(createRewardTransactionRequestDto.getBrandType())
                .userId(createRewardTransactionRequestDto.getUserId())
                .totalCostPoint(createRewardTransactionRequestDto.getTotalCostPoint())
                .quantity(createRewardTransactionRequestDto.getQuantity())
                .rewardCategory(createRewardTransactionRequestDto.getRewardCategory())
                .rewardTransactionStatus(createRewardTransactionRequestDto.getRewardTransactionStatus())
                .build();
    }
}
