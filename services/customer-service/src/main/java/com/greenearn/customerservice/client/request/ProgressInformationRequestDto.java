package com.greenearn.customerservice.client.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.customerservice.client.enums.ChallengeDuration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProgressInformationRequestDto {
    private UUID userId;
    private LocalDateTime userJoinedDateTime;
    private ChallengeDuration challengeDuration;
}
