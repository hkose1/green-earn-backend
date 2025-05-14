package com.greenearn.customerservice.mapper;

import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class BottleTransactionMapper {

    public BottleTransactionEntity mapTransactionRequestDtoToEntity(BottleTransactionRequestDto bottleTransactionRequestDto) {
        return BottleTransactionEntity.builder()
                .customerId(bottleTransactionRequestDto.getCustomerId())
                .containerId(bottleTransactionRequestDto.getContainerId())
                .numberOfSmallBottles(bottleTransactionRequestDto.getNumberOfSmallBottles())
                .numberOfMediumBottles(bottleTransactionRequestDto.getNumberOfMediumBottles())
                .numberOfLargeBottles(bottleTransactionRequestDto.getNumberOfLargeBottles())
                .earnedPoints(bottleTransactionRequestDto.getPoints())
                .build();
    }
}
