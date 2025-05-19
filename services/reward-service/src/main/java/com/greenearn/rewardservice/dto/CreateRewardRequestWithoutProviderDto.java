package com.greenearn.rewardservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.RewardCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class CreateRewardRequestWithoutProviderDto {
    private Integer stock;
    private String title;
    private String description;
    private String imageUrl;
    private Integer costPoints;
    private Boolean isActive;
    private RewardCategory rewardCategory;
}
