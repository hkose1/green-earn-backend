package com.greenearn.rewardservice.mapper;

import com.greenearn.rewardservice.dto.CreateRewardProviderRequestDto;
import com.greenearn.rewardservice.dto.RewardProviderResponseDto;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardProviderMapper {

    public static RewardProviderResponseDto map2ResponseDto(RewardProviderEntity rewardProvider) {
        return RewardProviderResponseDto.builder()
                .id(rewardProvider.getId())
                .rewardResponseDtoList(
                        rewardProvider.getRewards()
                                .stream()
                                .map(rewardEntity -> RewardMapper.map2ResponseDto(rewardEntity))
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

    public static RewardProviderEntity mapCreateRewardRequestDto2Entity(CreateRewardProviderRequestDto createRewardProviderRequestDto) {
        RewardProviderEntity rewardProvider =  RewardProviderEntity.builder()
                .name(createRewardProviderRequestDto.getName())
                .description(createRewardProviderRequestDto.getDescription())
                .logoUrl(createRewardProviderRequestDto.getLogoUrl())
                .brandType(createRewardProviderRequestDto.getBrandType())
                .build();

        return rewardProvider;
    }
}
