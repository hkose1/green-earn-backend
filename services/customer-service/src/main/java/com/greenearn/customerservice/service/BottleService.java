package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottlePointsInfoResponseDto;
import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.entity.BottleEntity;
import com.greenearn.customerservice.enums.BottleSizeType;
import com.greenearn.customerservice.mapper.BottleMapper;
import com.greenearn.customerservice.repository.BottleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BottleService {

    private final BottleRepository bottleRepository;
    private final BottleMapper bottleMapper;

    public BottleResponseDto getBottleBySizeType(BottleSizeType bottleSizeType) {
        return bottleMapper.map2ResponseDto(
                bottleRepository.findByBottleSize(bottleSizeType)
                        .orElseThrow(() -> new RuntimeException("Bottle not found with type: " + bottleSizeType))
        );
    }

    public BottlePointsInfoResponseDto getBottlePointInfo() {
        return BottlePointsInfoResponseDto.builder()
                .smallBottlePoints(getBottleBySizeType(BottleSizeType.SMALL).getPoints())
                .mediumBottlePoints(getBottleBySizeType(BottleSizeType.MEDIUM).getPoints())
                .largeBottlePoints(getBottleBySizeType(BottleSizeType.LARGE).getPoints())
                .build();
    }

    public List<BottleResponseDto> getBottles() {
        return bottleRepository.findAll()
                .stream()
                .map(bottleEntity -> bottleMapper.map2ResponseDto(bottleEntity))
                .toList();
    }

    @Transactional
    public void updateBottlePointById(UUID bottleId, Integer newBottlePoint) {
        BottleEntity bottleEntity = bottleRepository.findById(bottleId)
                .orElseThrow(() -> new RuntimeException("Bottle not found with id: " + bottleId));
        if (newBottlePoint < 0) {
            throw new RuntimeException("New bottle point is negative. Bottle point can not be updated");
        }
        bottleEntity.setPoints(newBottlePoint);
        bottleRepository.save(bottleEntity);
    }
}
