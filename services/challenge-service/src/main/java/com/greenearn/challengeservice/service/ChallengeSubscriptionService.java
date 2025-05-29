package com.greenearn.challengeservice.service;

import com.greenearn.challengeservice.entity.ChallengeSubscriptionEntity;
import com.greenearn.challengeservice.repository.ChallengeSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class ChallengeSubscriptionService {
    private final ChallengeSubscriptionRepository challengeSubscriptionRepository;


    public List<UUID> getSubscribedCustomerIds(UUID challengeId) {
        return challengeSubscriptionRepository.findByChallengeId(challengeId)
                .stream()
                .map(ChallengeSubscriptionEntity::getUserId)
                .toList();
    }

    public Boolean findIsSubscribedByChallengeIdAndUserId(UUID challengeId, UUID userId) {
        ChallengeSubscriptionEntity challengeSubscriptionEntity = challengeSubscriptionRepository
                .findByChallengeIdAndUserId(challengeId, userId).orElse(null);
        return challengeSubscriptionEntity != null;
    }
}
