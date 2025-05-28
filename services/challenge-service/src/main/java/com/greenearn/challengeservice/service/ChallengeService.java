package com.greenearn.challengeservice.service;

import com.greenearn.challengeservice.client.CustomerServiceClient;
import com.greenearn.challengeservice.client.request.ProgressInformationRequestDto;
import com.greenearn.challengeservice.client.response.ProgressInformationResponseDto;
import com.greenearn.challengeservice.dto.*;
import com.greenearn.challengeservice.entity.ChallengeConditionEntity;
import com.greenearn.challengeservice.entity.ChallengeEntity;
import com.greenearn.challengeservice.entity.ChallengeSubscriptionEntity;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import com.greenearn.challengeservice.enums.ChallengeSubscriptionProgressStatus;
import com.greenearn.challengeservice.mapper.ChallengeConditionMapper;
import com.greenearn.challengeservice.mapper.ChallengeMapper;
import com.greenearn.challengeservice.repository.ChallengeRepository;
import com.greenearn.challengeservice.repository.ChallengeSubscriptionRepository;
import com.greenearn.challengeservice.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final ChallengeConditionMapper challengeConditionMapper;
    private final ChallengeSubscriptionRepository challengeSubscriptionRepository;
    private final CustomerServiceClient customerServiceClient;

    @Transactional
    public void createChallenge(CreateChallengeRequestDto createChallengeRequestDto) {
        try {
            ChallengeEntity challengeEntity = challengeMapper.mapCreateRequest2Entity(createChallengeRequestDto);

            ChallengeConditionEntity challengeConditionEntity =
                    challengeConditionMapper.map2Entity(createChallengeRequestDto.getChallengeCondition());
            challengeConditionEntity.setChallenge(challengeEntity);
            challengeEntity.setChallengeCondition(challengeConditionEntity);
            challengeRepository.save(challengeEntity);
        } catch (Exception e) {
            log.error("Failed to create challenge and its condition", e);
        }
    }

    public List<ChallengeResponseAdminDto> getAllChallengesForAdmin() {
        try {
            List<ChallengeResponseAdminDto> challengeResponseAdminDtoList = challengeRepository.findAll()
                    .stream()
                    .map(challengeEntity -> challengeMapper.mapEntity2ResponseAdminDto(challengeEntity))
                    .toList();
            return challengeResponseAdminDtoList;
        } catch (Exception e) {
            log.error("Failed to get all challenges for admin", e);
            return new ArrayList<>();
        }
    }

    public List<ChallengeResponseDto> getAllChallenges() {
        try {
            List<ChallengeResponseDto> challengeResponseDtoList = challengeRepository.findAll()
                    .stream()
                    .map(challengeEntity -> challengeMapper.mapEntity2ResponseDto(challengeEntity))
                    .toList();
            return challengeResponseDtoList;
        } catch (Exception e) {
            log.error("Failed to get all challenges", e);
            return new ArrayList<>();
        }
    }

    public void subscribe(Authentication authentication, SubscribeToChallengeRequestDto subscribeRequestDto) {
        ChallengeEntity challengeEntity = challengeRepository.findById(subscribeRequestDto.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + subscribeRequestDto.getChallengeId()));
        if (isChallengeHasExpired(challengeEntity)) {
            challengeEntity.setChallengeStatus(ChallengeStatus.INACTIVE);
            challengeRepository.save(challengeEntity);
            throw new RuntimeException("Challenge has expired and now become inactive");
        }
        if (challengeEntity.getChallengeStatus() == ChallengeStatus.INACTIVE) {
            throw new RuntimeException("Challenge is inactive");
        }

        UUID currentUserId = null;
        try {
            currentUserId = UserUtils.getCurrentUserId(authentication);
        } catch (Exception e) {
            log.error("Failed to get current user: ", e);
            throw new RuntimeException("Failed to get current user: error: ", e);
        }
        try {
            ChallengeSubscriptionEntity challengeSubscriptionEntity = ChallengeSubscriptionEntity.builder()
                    .challengeId(subscribeRequestDto.getChallengeId())
                    .userId(currentUserId)
                    .challengeSubscriptionProgressStatus(ChallengeSubscriptionProgressStatus.UN_COMPLETED)
                    .build();
            challengeSubscriptionRepository.save(challengeSubscriptionEntity);
        } catch (Exception e) {
            log.error("Failed to subscribe to challenge: ", e);
            throw new RuntimeException("Failed to subscribe to challenge: " + subscribeRequestDto.getChallengeId());
        }
    }

    private boolean isChallengeHasExpired(ChallengeEntity challengeEntity) {
        return LocalDateTime.now().isAfter(challengeEntity.getCreatedAt());
    }

    public ProgressOnChallengeResponseDto getProgressOnChallenge(UUID challengeId, Authentication authentication) {
        UUID currentUserId = null;
        try {
            currentUserId = UserUtils.getCurrentUserId(authentication);
        } catch (Exception e) {
            log.error("Failed to get current user: ", e);
            throw new RuntimeException("Failed to get current user: error: ", e);
        }
        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + challengeId));

        ChallengeSubscriptionEntity challengeSubscriptionEntity =
                challengeSubscriptionRepository.findByChallengeIdAndUserId(challengeId, currentUserId)
                        .orElseThrow(() -> new RuntimeException("Challenge subscription not found "));

        LocalDateTime userJoinedDateTime = challengeSubscriptionEntity.getCreatedAt();
        ChallengeDuration durationForChallenge = challengeEntity.getChallengeDuration();

        ProgressInformationResponseDto responseDto = customerServiceClient.getProgressOnChallenge(
                ProgressInformationRequestDto.builder()
                        .userId(currentUserId)
                        .userJoinedDateTime(userJoinedDateTime)
                        .challengeDuration(durationForChallenge)
                        .build(),
                "Bearer " + UserUtils.getCurrentUserToken(authentication)
        ).getBody();

        return ProgressOnChallengeResponseDto.builder()
                .currentNumberOfSmallBottles(responseDto.getNumberOfSmallBottlesForJoinedChallenge())
                .currentNumberOfMediumBottles(responseDto.getNumberOfMediumBottlesForJoinedChallenge())
                .currentNumberOfLargeBottles(responseDto.getNumberOfLargeBottlesForJoinedChallenge())
                .build();

    }
}
