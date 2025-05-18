package com.greenearn.rewardservice.mapper;

import com.greenearn.rewardservice.dto.CreateRewardRequestDto;
import com.greenearn.rewardservice.dto.CreateRewardRequestWithoutProviderDto;
import com.greenearn.rewardservice.dto.RewardResponseDto;
import com.greenearn.rewardservice.dto.RewardWithoutProviderResponseDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardMapper {

    public static RewardResponseDto map2ResponseDto(RewardEntity rewardEntity) {
        return RewardResponseDto.builder()
                .id(rewardEntity.getId())
                .title(rewardEntity.getTitle())
                .description(rewardEntity.getDescription())
                .costPoints(rewardEntity.getCostPoints())
                .createdAt(rewardEntity.getCreatedAt())
                .updatedAt(rewardEntity.getUpdatedAt())
                .rewardCategory(rewardEntity.getRewardCategory())
                .rewardProvider(RewardProviderMapper.map2WithoutRewardsResponseDto(rewardEntity.getRewardProvider()))
                .isActive(rewardEntity.getIsActive())
                .imageUrl(rewardEntity.getImageUrl())
                .build();
    }

    public static RewardWithoutProviderResponseDto map2ResponseWithoutProviderDto(RewardEntity rewardEntity) {
        return RewardWithoutProviderResponseDto.builder()
                .id(rewardEntity.getId())
                .title(rewardEntity.getTitle())
                .description(rewardEntity.getDescription())
                .costPoints(rewardEntity.getCostPoints())
                .createdAt(rewardEntity.getCreatedAt())
                .updatedAt(rewardEntity.getUpdatedAt())
                .rewardCategory(rewardEntity.getRewardCategory())
                .isActive(rewardEntity.getIsActive())
                .imageUrl(rewardEntity.getImageUrl())
                .build();
    }

    public static RewardEntity mapCreateRequestDto2Entity(CreateRewardRequestDto createRewardRequestDto, RewardProviderEntity rewardProvider) {
        return RewardEntity.builder()
                .rewardProvider(rewardProvider)
                .isActive(createRewardRequestDto.getIsActive())
                .imageUrl(createRewardRequestDto.getImageUrl())
                .title(createRewardRequestDto.getTitle())
                .description(createRewardRequestDto.getDescription())
                .costPoints(createRewardRequestDto.getCostPoints())
                .rewardCategory(createRewardRequestDto.getRewardCategory())
                .build();
    }

    public static RewardEntity mapCreateRewardWithoutProviderDtoToEntity(CreateRewardRequestWithoutProviderDto dto) {
        return RewardEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .costPoints(dto.getCostPoints())
                .isActive(dto.getIsActive())
                .rewardCategory(dto.getRewardCategory())
                .build();
    }

    public static RewardEntity mapCreateRequestDto2Entity(CreateRewardRequestDto createRewardRequestDto) {
        return RewardEntity.builder()
                .isActive(createRewardRequestDto.getIsActive())
                .imageUrl(createRewardRequestDto.getImageUrl())
                .title(createRewardRequestDto.getTitle())
                .description(createRewardRequestDto.getDescription())
                .costPoints(createRewardRequestDto.getCostPoints())
                .rewardCategory(createRewardRequestDto.getRewardCategory())
                .build();
    }
}
