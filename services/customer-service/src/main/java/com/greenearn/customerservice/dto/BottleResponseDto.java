package com.greenearn.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.customerservice.enums.BottleSizeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class BottleResponseDto {
    private UUID id;
    private BottleSizeType bottleSize;
    private Integer points;
}
