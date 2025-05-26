package com.greenearn.challengeservice.repository;

import com.greenearn.challengeservice.entity.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, UUID> {
}
