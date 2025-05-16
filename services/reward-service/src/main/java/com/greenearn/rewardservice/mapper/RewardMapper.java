package com.greenearn.rewardservice.mapper;

import com.greenearn.rewardservice.dto.CreateRewardRequestDto;
import com.greenearn.rewardservice.dto.RewardResponseDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
                .rewardProviderResponseDto(RewardProviderMapper.map2ResponseDto(rewardEntity.getRewardProvider()))
                .isActive(rewardEntity.getIsActive())
                .imageUrl(rewardEntity.getImageUrl())
                .build();
    }

    public static RewardEntity mapCreateRequestDto2Entity(CreateRewardRequestDto createRewardRequestDto) {
        RewardProviderEntity rewardProvider = RewardProviderMapper.mapCreateRewardRequestDto2Entity(
                createRewardRequestDto.getCreateRewardProviderRequestDto()
        );
        RewardEntity rewardEntity =  RewardEntity.builder()
                .rewardProvider(rewardProvider)
                .isActive(createRewardRequestDto.getIsActive())
                .imageUrl(createRewardRequestDto.getImageUrl())
                .title(createRewardRequestDto.getTitle())
                .description(createRewardRequestDto.getDescription())
                .costPoints(createRewardRequestDto.getCostPoints())
                .rewardCategory(createRewardRequestDto.getRewardCategory())
                .build();
        if (rewardProvider.getRewards() == null) {
            rewardProvider.setRewards(new ArrayList<>());
        }
        rewardProvider.getRewards().add(rewardEntity);
        return rewardEntity;
    }
}
