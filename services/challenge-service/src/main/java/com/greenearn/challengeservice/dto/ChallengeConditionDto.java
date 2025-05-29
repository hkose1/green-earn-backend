package com.greenearn.challengeservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChallengeConditionDto {
    private Integer requiredNumberOfSmallBottles;
    private Integer requiredNumberOfMediumBottles;
    private Integer requiredNumberOfLargeBottles;
}
