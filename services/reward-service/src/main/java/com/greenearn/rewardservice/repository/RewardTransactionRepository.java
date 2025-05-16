package com.greenearn.rewardservice.repository;

import com.greenearn.rewardservice.entity.RewardTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardTransactionRepository extends JpaRepository<RewardTransactionEntity, UUID> {
}
