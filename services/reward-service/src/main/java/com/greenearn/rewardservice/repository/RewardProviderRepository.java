package com.greenearn.rewardservice.repository;

import com.greenearn.rewardservice.entity.RewardProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardProviderRepository extends JpaRepository<RewardProviderEntity, UUID> {
}
