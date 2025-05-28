package com.greenearn.customerservice.client.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class ProgressInformationResponseDto {
    private Integer numberOfSmallBottlesForJoinedChallenge;
    private Integer numberOfMediumBottlesForJoinedChallenge;
    private Integer numberOfLargeBottlesForJoinedChallenge;
}
