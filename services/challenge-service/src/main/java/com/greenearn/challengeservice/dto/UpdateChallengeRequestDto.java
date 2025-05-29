package com.greenearn.challengeservice.dto;


import com.greenearn.challengeservice.enums.ChallengeDuration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateChallengeRequestDto {
    private String title;
    private String description;
    private ChallengeDuration challengeDuration;
    private LocalDateTime endDate;
    private Integer returnedPoints;
    private ChallengeConditionDto challengeCondition;
}
