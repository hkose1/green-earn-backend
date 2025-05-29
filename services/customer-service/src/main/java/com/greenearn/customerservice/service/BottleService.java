package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottlePointsInfoResponseDto;
import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.entity.BottleEntity;
import com.greenearn.customerservice.enums.BottleSizeType;
import com.greenearn.customerservice.mapper.BottleMapper;
import com.greenearn.customerservice.repository.BottleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        BottleEntity smallBottleEntity = new BottleEntity();
        smallBottleEntity.setBottleSize(BottleSizeType.SMALL);
        smallBottleEntity.setPoints(5);
        bottleRepository.save(smallBottleEntity);
        BottleEntity mediumBottleEntity = new BottleEntity();
        mediumBottleEntity.setBottleSize(BottleSizeType.MEDIUM);
        mediumBottleEntity.setPoints(10);
        bottleRepository.save(mediumBottleEntity);
        BottleEntity largeBottleEntity = new BottleEntity();
        largeBottleEntity.setBottleSize(BottleSizeType.LARGE);
        largeBottleEntity.setPoints(15);
        bottleRepository.save(largeBottleEntity);
        return BottlePointsInfoResponseDto.builder()
                .smallBottlePoints(getBottleBySizeType(BottleSizeType.SMALL).getPoints())
                .mediumBottlePoints(getBottleBySizeType(BottleSizeType.MEDIUM).getPoints())
                .largeBottlePoints(getBottleBySizeType(BottleSizeType.LARGE).getPoints())
                .build();

    }
}
