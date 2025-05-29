package com.greenearn.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class BottlePointsInfoResponseDto {
    private Integer smallBottlePoints;
    private Integer mediumBottlePoints;
    private Integer largeBottlePoints;
}
