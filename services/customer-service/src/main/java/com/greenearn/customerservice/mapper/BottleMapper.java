package com.greenearn.customerservice.mapper;

import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.entity.BottleEntity;
import org.springframework.stereotype.Component;

@Component
public class BottleMapper {

    public BottleResponseDto map2ResponseDto(BottleEntity bottleEntity) {
        return BottleResponseDto.builder()
                .id(bottleEntity.getId())
                .bottleSize(bottleEntity.getBottleSize())
                .points(bottleEntity.getPoints())
                .build();
    }
}
