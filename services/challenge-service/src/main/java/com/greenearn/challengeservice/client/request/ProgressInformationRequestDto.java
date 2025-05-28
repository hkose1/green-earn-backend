package com.greenearn.challengeservice.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class ProgressInformationRequestDto {
    private UUID userId;
    private LocalDateTime userJoinedDateTime;
    private ChallengeDuration challengeDuration;
}
