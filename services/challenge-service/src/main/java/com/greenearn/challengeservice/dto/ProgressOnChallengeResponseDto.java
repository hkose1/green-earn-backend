package com.greenearn.challengeservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class ProgressOnChallengeResponseDto {
    private Integer currentNumberOfSmallBottles;
    private Integer currentNumberOfMediumBottles;
    private Integer currentNumberOfLargeBottles;
}
