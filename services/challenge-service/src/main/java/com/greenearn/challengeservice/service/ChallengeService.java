package com.greenearn.challengeservice.service;

import com.greenearn.challengeservice.client.AuthServiceClient;
import com.greenearn.challengeservice.client.CustomerServiceClient;
import com.greenearn.challengeservice.client.request.ProgressInformationRequestDto;
import com.greenearn.challengeservice.client.request.UpdatePointsAfterCompletedChallengeRequestDto;
import com.greenearn.challengeservice.client.response.ProgressInformationResponseDto;
import com.greenearn.challengeservice.dto.*;
import com.greenearn.challengeservice.entity.ChallengeConditionEntity;
import com.greenearn.challengeservice.entity.ChallengeEntity;
import com.greenearn.challengeservice.entity.ChallengeSubscriptionEntity;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeEventType;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import com.greenearn.challengeservice.enums.ChallengeSubscriptionProgressStatus;
import com.greenearn.challengeservice.event.ChallengeEvent;
import com.greenearn.challengeservice.mapper.ChallengeConditionMapper;
import com.greenearn.challengeservice.mapper.ChallengeMapper;
import com.greenearn.challengeservice.repository.ChallengeRepository;
import com.greenearn.challengeservice.repository.ChallengeSubscriptionRepository;
import com.greenearn.challengeservice.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final ChallengeConditionMapper challengeConditionMapper;
    private final ChallengeSubscriptionRepository challengeSubscriptionRepository;
    private final CustomerServiceClient customerServiceClient;
    private final ChallengeConditionService challengeConditionService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthServiceClient authServiceClient;

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

    @Transactional
    public void updateChallenge(UUID challengeId, UpdateChallengeRequestDto updateChallengeRequestDto) {
        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found with id " + challengeId));

        if (updateChallengeRequestDto.getChallengeCondition() != null) {
            ChallengeConditionEntity challengeCondition = challengeEntity.getChallengeCondition();
            if (challengeCondition == null) {
                challengeCondition = new ChallengeConditionEntity();
                challengeCondition.setChallenge(challengeEntity);
            }
            if (updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfSmallBottles() != null) {
                challengeCondition
                        .setRequiredNumberOfSmallBottles(updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfSmallBottles());
            }
            if (updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfMediumBottles() != null) {
                challengeCondition
                        .setRequiredNumberOfMediumBottles(updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfMediumBottles());
            }
            if (updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfLargeBottles() != null) {
                challengeCondition
                        .setRequiredNumberOfLargeBottles(updateChallengeRequestDto.getChallengeCondition().getRequiredNumberOfLargeBottles());
            }

            challengeEntity.setChallengeCondition(challengeCondition);
        }
        if (updateChallengeRequestDto.getChallengeDuration() != null) {
            challengeEntity.setChallengeDuration(updateChallengeRequestDto.getChallengeDuration());
        }
        if (updateChallengeRequestDto.getTitle() != null) {
            challengeEntity.setTitle(updateChallengeRequestDto.getTitle());
        }
        if (updateChallengeRequestDto.getDescription() != null) {
            challengeEntity.setDescription(updateChallengeRequestDto.getDescription());
        }
        if (updateChallengeRequestDto.getEndDate() != null) {
            challengeEntity.setEndDate(updateChallengeRequestDto.getEndDate());
        }
        if (updateChallengeRequestDto.getReturnedPoints() != null) {
            challengeEntity.setReturnedPoints(updateChallengeRequestDto.getReturnedPoints());
        }
        challengeRepository.save(challengeEntity);
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

    public ChallengeResponseAdminDto getChallengeByIdAdmin(UUID challengeId) {
        return challengeMapper.mapEntity2ResponseAdminDto(
                challengeRepository.findById(challengeId)
                        .orElseThrow(() -> new RuntimeException("Failed to find challenge with id: " + challengeId))
        );
    }

    public List<ChallengeResponseDto> getAllChallenges(Authentication authentication) {
        try {
            UUID currentUserId = UserUtils.getCurrentUserId(authentication);
            List<ChallengeResponseDto> challengeResponseDtoList = challengeRepository.findAll()
                    .stream()
                    .map(challengeEntity -> challengeMapper.mapEntity2ResponseDto(challengeEntity, currentUserId))
                    .toList();
            return challengeResponseDtoList;
        } catch (Exception e) {
            log.error("Failed to get all challenges", e);
            return new ArrayList<>();
        }
    }

    public ChallengeResponseDto getChallengeById(UUID challengeId, Authentication authentication) {
        UUID currentUserId = UserUtils.getCurrentUserId(authentication);
        return challengeMapper.mapEntity2ResponseDto(
                challengeRepository.findById(challengeId)
                        .orElseThrow(() -> new RuntimeException("Failed to find challenge with id: " + challengeId)),
                currentUserId
        );
    }

    public void subscribe(Authentication authentication, SubscribeToChallengeRequestDto subscribeRequestDto) {
        ChallengeEntity challengeEntity = challengeRepository.findById(subscribeRequestDto.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + subscribeRequestDto.getChallengeId()));

        if (challengeEntity.getChallengeStatus() == ChallengeStatus.INACTIVE) {
            throw new RuntimeException("Challenge is inactive");
        }

        if (isChallengeHasExpired(challengeEntity)) {
            challengeEntity.setChallengeStatus(ChallengeStatus.INACTIVE);
            challengeRepository.save(challengeEntity);
            throw new RuntimeException("Challenge has expired and now become inactive");
        }

        UUID currentUserId = null;
        try {
            currentUserId = UserUtils.getCurrentUserId(authentication);
        } catch (Exception e) {
            log.error("Failed to get current user: ", e);
            throw new RuntimeException("Failed to get current user: error: ", e);
        }

        if (challengeSubscriptionRepository
                .findByChallengeIdAndUserId(challengeEntity.getId(), currentUserId).isPresent()) {
            throw new RuntimeException("Challenge has already been subscribed");
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
        return LocalDateTime.now().isAfter(challengeEntity.getEndDate());
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

        if (responseDto == null) {
            throw new RuntimeException("Failed to get progress on challenge with id: " + challengeId);
        }

        ProgressOnChallengeResponseDto progress = ProgressOnChallengeResponseDto.builder()
                .currentNumberOfSmallBottles(
                        responseDto.getNumberOfSmallBottlesForJoinedChallenge() != null ?
                                responseDto.getNumberOfSmallBottlesForJoinedChallenge() : 0
                )
                .currentNumberOfMediumBottles(
                        responseDto.getNumberOfMediumBottlesForJoinedChallenge() != null ?
                                responseDto.getNumberOfMediumBottlesForJoinedChallenge() : 0
                )
                .currentNumberOfLargeBottles(
                        responseDto.getNumberOfLargeBottlesForJoinedChallenge() != null ?
                                responseDto.getNumberOfLargeBottlesForJoinedChallenge() : 0
                )
                .challengeSubscriptionProgressStatus(challengeSubscriptionEntity.getChallengeSubscriptionProgressStatus())
                .build();

        if (challengeSubscriptionEntity.getChallengeSubscriptionProgressStatus() == ChallengeSubscriptionProgressStatus.UN_COMPLETED) {
            ChallengeConditionDto challengeConditionDto =
                    challengeConditionService.getChallengeConditionByChallengeId(challengeId);
            if (isChallengeCompleted(challengeConditionDto, progress)) {
                challengeSubscriptionEntity.setChallengeSubscriptionProgressStatus(
                        ChallengeSubscriptionProgressStatus.COMPLETED
                );
                challengeSubscriptionRepository.save(challengeSubscriptionEntity);
                progress.setChallengeSubscriptionProgressStatus(
                        ChallengeSubscriptionProgressStatus.COMPLETED
                );
                customerServiceClient.updatePointsAfterCompleteChallenge(
                        UpdatePointsAfterCompletedChallengeRequestDto.builder()
                                .earnedPointsAfterCompletedChallenge(challengeEntity.getReturnedPoints())
                                .build(),
                        "Bearer " + UserUtils.getCurrentUserToken(authentication)
                );
                eventPublisher.publishEvent(
                        new ChallengeEvent(
                                authServiceClient
                                        .getCurrentUser("Bearer " + UserUtils.getCurrentUserToken(authentication))
                                        .getBody().data(),
                                challengeEntity,
                                ChallengeEventType.COMPLETED,
                                Map.of()
                        )
                );

            }
        }

        return progress;

    }

    private boolean isChallengeCompleted(ChallengeConditionDto challengeConditionDto,
                                             ProgressOnChallengeResponseDto progressDto) {
        return (progressDto.getCurrentNumberOfSmallBottles() >= challengeConditionDto.getRequiredNumberOfSmallBottles())
                && (progressDto.getCurrentNumberOfMediumBottles() >= challengeConditionDto.getRequiredNumberOfMediumBottles())
                && (progressDto.getCurrentNumberOfLargeBottles() >= challengeConditionDto.getRequiredNumberOfLargeBottles());
    }
}
