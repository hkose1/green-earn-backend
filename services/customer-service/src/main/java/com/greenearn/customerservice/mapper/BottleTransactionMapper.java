package com.greenearn.customerservice.mapper;

import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.dto.BottleTransactionResponseDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import com.greenearn.customerservice.service.CurrentCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BottleTransactionMapper {

    private final CurrentCustomerService currentCustomerService;

    public BottleTransactionEntity mapTransactionRequestDtoToEntity(BottleTransactionRequestDto bottleTransactionRequestDto, Authentication authentication) {
        return BottleTransactionEntity.builder()
                .customerId(currentCustomerService.getCurrentCustomerId(authentication))
                .containerId(bottleTransactionRequestDto.getContainerId())
                .numberOfSmallBottles(bottleTransactionRequestDto.getNumberOfSmallBottles())
                .numberOfMediumBottles(bottleTransactionRequestDto.getNumberOfMediumBottles())
                .numberOfLargeBottles(bottleTransactionRequestDto.getNumberOfLargeBottles())
                .earnedPoints(bottleTransactionRequestDto.getPoints())
                .build();
    }

    public BottleTransactionResponseDto map2ResponseDto(BottleTransactionEntity bottleTransactionEntity) {
        return BottleTransactionResponseDto.builder()
                .id(bottleTransactionEntity.getId())
                .createdAt(bottleTransactionEntity.getCreatedAt())
                .bottleTransactionStatus(bottleTransactionEntity.getBottleTransactionStatus())
                .customerId(bottleTransactionEntity.getCustomerId())
                .containerId(bottleTransactionEntity.getContainerId())
                .numberOfSmallBottles(bottleTransactionEntity.getNumberOfSmallBottles())
                .numberOfMediumBottles(bottleTransactionEntity.getNumberOfMediumBottles())
                .numberOfLargeBottles(bottleTransactionEntity.getNumberOfLargeBottles())
                .earnedPoints(bottleTransactionEntity.getEarnedPoints())
                .build();
    }
}
