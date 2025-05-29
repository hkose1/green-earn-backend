package com.greenearn.challengeservice.dto;


import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ChallengeResponseDto {
    private UUID id;
    private LocalDateTime createdAt;
    private String title;
    private LocalDateTime endDate;
    private Integer returnedPoints;
    private String description;
    private ChallengeDuration challengeDuration;
    private ChallengeStatus challengeStatus;
    private ChallengeConditionDto challengeConditionDto;
    private Boolean isSubscribed;

}
