package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.dto.projection.TopCustomerDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BottleTransactionRepository extends JpaRepository<BottleTransactionEntity, UUID> {

    List<BottleTransactionEntity> findBottleTransactionEntitiesByCustomerId(UUID customerId);
    List<BottleTransactionEntity> findByCustomerIdAndCreatedAtBetween(UUID customerId, LocalDateTime start, LocalDateTime end);

    @Query(value = """
            SELECT customer_id AS customerId, SUM(earned_points) AS totalPoints
            FROM bottle_transactions
            WHERE created_at >= :startDate
            GROUP BY customer_id
            ORDER BY totalPoints DESC
            LIMIT 5
    """, nativeQuery = true)
    List<TopCustomerDto> findTop5CustomersSince(@Param("startDate") LocalDateTime startDate);
}
