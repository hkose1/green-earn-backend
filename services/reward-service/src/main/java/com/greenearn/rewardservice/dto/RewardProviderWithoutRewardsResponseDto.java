package com.greenearn.rewardservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class RewardProviderWithoutRewardsResponseDto {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BrandType brandType;
    private String name;
    private String description;
    private String logoUrl;
}
