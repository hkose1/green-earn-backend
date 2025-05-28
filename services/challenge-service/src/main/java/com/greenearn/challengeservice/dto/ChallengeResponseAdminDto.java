package com.greenearn.challengeservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class ChallengeResponseAdminDto {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private Integer returnedPoints;
    private LocalDateTime endDate;
    private String description;
    private ChallengeDuration challengeDuration;
    private ChallengeStatus challengeStatus;
    private List<UUID> subscribedCustomers;
    private ChallengeConditionDto challengeConditionDto;
}
