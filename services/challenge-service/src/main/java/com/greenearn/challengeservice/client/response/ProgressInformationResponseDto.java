package com.greenearn.challengeservice.client.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProgressInformationResponseDto {
    private Integer numberOfSmallBottlesForJoinedChallenge;
    private Integer numberOfMediumBottlesForJoinedChallenge;
    private Integer numberOfLargeBottlesForJoinedChallenge;
}
