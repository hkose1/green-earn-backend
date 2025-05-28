package com.greenearn.challengeservice.repository;

import com.greenearn.challengeservice.entity.ChallengeConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChallengeConditionRepository extends JpaRepository<ChallengeConditionEntity, UUID> {

    Optional<ChallengeConditionEntity> findByChallengeId(UUID challengeId);
}
