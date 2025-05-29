package com.greenearn.customerservice.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UpdatePointsAfterCompletedChallengeRequestDto {
    private Integer earnedPointsAfterCompletedChallenge;
}
