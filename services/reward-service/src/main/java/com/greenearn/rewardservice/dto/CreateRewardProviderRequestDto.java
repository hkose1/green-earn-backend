package com.greenearn.rewardservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class CreateRewardProviderRequestDto {
    private BrandType brandType;
    private String name;
    private String description;
    private String logoUrl;
    private List<CreateRewardRequestWithoutProviderDto> rewards;
}
