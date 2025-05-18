package com.greenearn.rewardservice.mapper;

import com.greenearn.rewardservice.dto.CreateRewardProviderRequestDto;
import com.greenearn.rewardservice.dto.RewardProviderResponseDto;
import com.greenearn.rewardservice.dto.RewardProviderWithoutRewardsResponseDto;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardProviderMapper {

    public static RewardProviderResponseDto map2ResponseDto(RewardProviderEntity rewardProvider) {
        return RewardProviderResponseDto.builder()
                .id(rewardProvider.getId())
                .providedRewards(
                        rewardProvider.getRewards()
                                .stream()
                                .map(rewardEntity -> RewardMapper.map2ResponseWithoutProviderDto(rewardEntity))
                                .toList()
                )
                .name(rewardProvider.getName())
                .description(rewardProvider.getDescription())
                .createdAt(rewardProvider.getCreatedAt())
                .updatedAt(rewardProvider.getUpdatedAt())
                .brandType(rewardProvider.getBrandType())
                .logoUrl(rewardProvider.getLogoUrl())
                .build();
    }

    public static RewardProviderWithoutRewardsResponseDto map2WithoutRewardsResponseDto(RewardProviderEntity rewardProvider) {
        return RewardProviderWithoutRewardsResponseDto.builder()
                .id(rewardProvider.getId())
                .name(rewardProvider.getName())
                .description(rewardProvider.getDescription())
                .createdAt(rewardProvider.getCreatedAt())
                .updatedAt(rewardProvider.getUpdatedAt())
                .brandType(rewardProvider.getBrandType())
                .logoUrl(rewardProvider.getLogoUrl())
                .build();
    }

    public static RewardProviderEntity mapCreateRewardRequestDto2Entity(CreateRewardProviderRequestDto createRewardProviderRequestDto) {
        return RewardProviderEntity.builder()
                .name(createRewardProviderRequestDto.getName())
                .description(createRewardProviderRequestDto.getDescription())
                .logoUrl(createRewardProviderRequestDto.getLogoUrl())
                .brandType(createRewardProviderRequestDto.getBrandType())
                .build();
    }


}
