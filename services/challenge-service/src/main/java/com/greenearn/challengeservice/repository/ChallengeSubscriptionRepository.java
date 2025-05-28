package com.greenearn.challengeservice.repository;

import com.greenearn.challengeservice.entity.ChallengeSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChallengeSubscriptionRepository extends JpaRepository<ChallengeSubscriptionEntity, UUID> {
    List<ChallengeSubscriptionEntity> findByChallengeId(UUID challengeId);
    Optional<ChallengeSubscriptionEntity> findByChallengeIdAndUserId(UUID challengeId, UUID userId);
}
