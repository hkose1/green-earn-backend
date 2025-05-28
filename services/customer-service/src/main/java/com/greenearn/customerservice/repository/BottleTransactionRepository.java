package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.entity.BottleTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BottleTransactionRepository extends JpaRepository<BottleTransactionEntity, UUID> {

    List<BottleTransactionEntity> findBottleTransactionEntitiesByCustomerId(UUID customerId);
    List<BottleTransactionEntity> findByCustomerIdAndCreatedAtBetween(UUID customerId, LocalDateTime start, LocalDateTime end);
}
