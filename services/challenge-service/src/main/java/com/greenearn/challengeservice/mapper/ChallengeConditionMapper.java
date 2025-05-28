package com.greenearn.challengeservice.mapper;

import com.greenearn.challengeservice.dto.ChallengeConditionDto;
import com.greenearn.challengeservice.entity.ChallengeConditionEntity;
import org.springframework.stereotype.Component;

@Component
public class ChallengeConditionMapper {

    public ChallengeConditionEntity map2Entity(ChallengeConditionDto challengeConditionDto) {
        return ChallengeConditionEntity.builder()
                .requiredNumberOfSmallBottles(challengeConditionDto.getRequiredNumberOfSmallBottles())
                .requiredNumberOfMediumBottles(challengeConditionDto.getRequiredNumberOfMediumBottles())
                .requiredNumberOfLargeBottles(challengeConditionDto.getRequiredNumberOfLargeBottles())
                .build();
    }

    public ChallengeConditionDto map2Dto(ChallengeConditionEntity challengeConditionEntity) {
        return ChallengeConditionDto.builder()
                .requiredNumberOfSmallBottles(challengeConditionEntity.getRequiredNumberOfSmallBottles())
                .requiredNumberOfMediumBottles(challengeConditionEntity.getRequiredNumberOfMediumBottles())
                .requiredNumberOfLargeBottles(challengeConditionEntity.getRequiredNumberOfLargeBottles())
                .build();
    }

}
