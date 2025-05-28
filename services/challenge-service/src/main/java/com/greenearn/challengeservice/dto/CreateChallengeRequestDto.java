package com.greenearn.challengeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class CreateChallengeRequestDto {
    private String title;
    private String description;
    private ChallengeDuration challengeDuration;
    private ChallengeStatus challengeStatus;
    private LocalDateTime endDate;
    private Integer returnedPoints;
    private ChallengeConditionDto challengeCondition;
}
