package com.greenearn.mailservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SendChallengeCompletedDto {
    private String to;
    private String name;
    private String challengeTitle;
    private String challengeDescription;
    private Integer earnedPointsFromChallenge;
}
