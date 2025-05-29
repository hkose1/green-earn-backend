package com.greenearn.challengeservice.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UpdatePointsAfterCompletedChallengeRequestDto {
    private Integer earnedPointsAfterCompletedChallenge;
}
