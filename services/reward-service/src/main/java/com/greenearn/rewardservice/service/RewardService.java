package com.greenearn.rewardservice.service;

import com.greenearn.rewardservice.client.CustomerServiceClient;
import com.greenearn.rewardservice.client.request.UpdateCustomerPointsRequestDto;
import com.greenearn.rewardservice.dto.CreateRewardRequestDto;
import com.greenearn.rewardservice.dto.PurchaseRewardRequestDto;
import com.greenearn.rewardservice.dto.RewardResponseDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import com.greenearn.rewardservice.entity.RewardTransactionEntity;
import com.greenearn.rewardservice.enums.RewardTransactionStatus;
import com.greenearn.rewardservice.mapper.RewardMapper;
import com.greenearn.rewardservice.mapper.RewardTransactionMapper;
import com.greenearn.rewardservice.repository.RewardProviderRepository;
import com.greenearn.rewardservice.repository.RewardRepository;
import com.greenearn.rewardservice.repository.RewardTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private final RewardRepository rewardRepository;
    private final RewardProviderRepository rewardProviderRepository;
    private final CustomerServiceClient customerServiceClient;
    private final RewardTransactionRepository rewardTransactionRepository;
    private final RewardTransactionHelperService rewardTransactionHelperService;

    public void createReward(CreateRewardRequestDto dto) {
        RewardProviderEntity provider = rewardProviderRepository.findById(dto.getRewardProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Reward provider not found"));

        RewardEntity reward = RewardMapper.mapCreateRequestDto2Entity(dto, provider);
        rewardRepository.save(reward);
    }

    public List<RewardResponseDto> getAllRewards() {
        return rewardRepository.findAll().stream()
                .sorted(Comparator.comparing(RewardEntity::getCreatedAt).reversed())
                .map(rewardEntity -> RewardMapper.map2ResponseDto(rewardEntity))
                .toList();
    }

    public RewardResponseDto getRewardById(UUID id) {
        return RewardMapper.map2ResponseDto(
                rewardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reward not found with id: " + id))
        );
    }

    @Transactional(rollbackOn = Exception.class)
    public void purchaseReward(Authentication authentication, PurchaseRewardRequestDto purchaseRewardRequestDto) {
        UUID userId = UtilService.getLoggedInUserId(authentication);
        String token = ((Jwt) authentication.getPrincipal()).getTokenValue();

        RewardEntity reward = rewardRepository.findById(purchaseRewardRequestDto.getRewardId())
                .orElseThrow(() -> new RuntimeException("Reward not found with id: " + purchaseRewardRequestDto.getRewardId()));

        if (!Boolean.TRUE.equals(reward.getIsActive())) {
            rewardTransactionHelperService.saveFailedTransaction(
                    userId, reward, purchaseRewardRequestDto,
                    RewardTransactionStatus.FAILED, "Reward is not active"
            );
            throw new RuntimeException("Reward is not active");
        }

        if (reward.getStock() <= 0 || purchaseRewardRequestDto.getQuantity() > reward.getStock()) {
            rewardTransactionHelperService.saveFailedTransaction(
                    userId, reward, purchaseRewardRequestDto,
                    RewardTransactionStatus.FAILED, "There is no stock available for this purchase"
            );
            throw new RuntimeException("There is no stock available for this purchase");
        }

        Integer totalCostOnPurchase = purchaseRewardRequestDto.getQuantity() * reward.getCostPoints();
        Integer customerPoint = customerServiceClient.getMyPoints("Bearer " + token).getBody();

        if (customerPoint < totalCostOnPurchase) {
            rewardTransactionHelperService.saveFailedTransaction(
                    userId, reward, purchaseRewardRequestDto,
                    RewardTransactionStatus.FAILED, "Not enough points"
            );
            throw new RuntimeException("Customer point not enough");
        }

        reward.setStock(reward.getStock() - purchaseRewardRequestDto.getQuantity());
        UpdateCustomerPointsRequestDto updateCustomerPointsRequestDto = UpdateCustomerPointsRequestDto.builder()
                .totalCostPoints(totalCostOnPurchase)
                .build();
        RewardTransactionEntity rewardTransaction = RewardTransactionEntity.builder()
                .userId(userId)
                .rewardTitle(reward.getTitle())
                .rewardTransactionStatus(RewardTransactionStatus.SUCCESS)
                .rewardCategory(reward.getRewardCategory())
                .brandType(reward.getRewardProvider().getBrandType())
                .quantity(purchaseRewardRequestDto.getQuantity())
                .totalCostPoint(totalCostOnPurchase)
                .build();
        customerServiceClient.updateMyPoints(updateCustomerPointsRequestDto, "Bearer " + token);
        rewardTransactionRepository.save(rewardTransaction);
    }
}
