package com.greenearn.rewardservice.service;

import com.greenearn.rewardservice.dto.PurchaseRewardRequestDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardTransactionEntity;
import com.greenearn.rewardservice.enums.RewardTransactionStatus;
import com.greenearn.rewardservice.repository.RewardTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RewardTransactionHelperService {

    private final RewardTransactionRepository rewardTransactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedTransaction(UUID userId, RewardEntity reward,
                                      PurchaseRewardRequestDto dto, RewardTransactionStatus status,
                                      String failureMessage) {
        rewardTransactionRepository.save(
                RewardTransactionEntity.builder()
                        .userId(userId)
                        .failureMessage(failureMessage)
                        .rewardTransactionStatus(status)
                        .rewardCategory(reward.getRewardCategory())
                        .brandType(reward.getRewardProvider().getBrandType())
                        .quantity(dto.getQuantity())
                        .totalCostPoint(dto.getQuantity() * reward.getCostPoints())
                        .build()
        );
    }
}
