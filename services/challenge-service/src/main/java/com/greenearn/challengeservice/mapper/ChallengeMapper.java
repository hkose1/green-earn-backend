package com.greenearn.challengeservice.mapper;

import com.greenearn.challengeservice.dto.ChallengeResponseAdminDto;
import com.greenearn.challengeservice.dto.ChallengeResponseDto;
import com.greenearn.challengeservice.dto.CreateChallengeRequestDto;
import com.greenearn.challengeservice.entity.ChallengeEntity;
import com.greenearn.challengeservice.service.ChallengeConditionService;
import com.greenearn.challengeservice.service.ChallengeSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeMapper {

    private final ChallengeSubscriptionService challengeSubscriptionService;
    private final ChallengeConditionService challengeConditionService;

    public ChallengeEntity mapCreateRequest2Entity(CreateChallengeRequestDto createChallengeRequestDto) {
        ChallengeEntity challengeEntity = ChallengeEntity.builder()
                .challengeDuration(createChallengeRequestDto.getChallengeDuration())
                .challengeStatus(createChallengeRequestDto.getChallengeStatus())
                .description(createChallengeRequestDto.getDescription())
                .title(createChallengeRequestDto.getTitle())
                .endDate(createChallengeRequestDto.getEndDate())
                .returnedPoints(createChallengeRequestDto.getReturnedPoints())
                .build();
        return challengeEntity;
    }

    public ChallengeResponseAdminDto mapEntity2ResponseAdminDto(ChallengeEntity challengeEntity) {
        return ChallengeResponseAdminDto.builder()
                .challengeDuration(challengeEntity.getChallengeDuration())
                .id(challengeEntity.getId())
                .createdAt(challengeEntity.getCreatedAt())
                .updatedAt(challengeEntity.getUpdatedAt())
                .description(challengeEntity.getDescription())
                .title(challengeEntity.getTitle())
                .challengeStatus(challengeEntity.getChallengeStatus())
                .endDate(challengeEntity.getEndDate())
                .returnedPoints(challengeEntity.getReturnedPoints())
                .challengeConditionDto(challengeConditionService.getChallengeConditionByChallengeId(challengeEntity.getId()))
                .subscribedCustomers(challengeSubscriptionService.getSubscribedCustomerIds(challengeEntity.getId()))
                .build();
    }

    public ChallengeResponseDto mapEntity2ResponseDto(ChallengeEntity challengeEntity) {
        return ChallengeResponseDto.builder()
                .challengeDuration(challengeEntity.getChallengeDuration())
                .id(challengeEntity.getId())
                .endDate(challengeEntity.getEndDate())
                .returnedPoints(challengeEntity.getReturnedPoints())
                .createdAt(challengeEntity.getCreatedAt())
                .challengeStatus(challengeEntity.getChallengeStatus())
                .description(challengeEntity.getDescription())
                .challengeConditionDto(challengeConditionService.getChallengeConditionByChallengeId(challengeEntity.getId()))
                .title(challengeEntity.getTitle())
                .build();
    }
}
