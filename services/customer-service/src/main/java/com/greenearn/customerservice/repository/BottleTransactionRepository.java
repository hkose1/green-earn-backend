package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.dto.projection.DailyBottleStatsProjectionDto;
import com.greenearn.customerservice.dto.projection.DailyPointProjectionDto;
import com.greenearn.customerservice.dto.projection.TopCustomerDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BottleTransactionRepository extends JpaRepository<BottleTransactionEntity, UUID> {

    List<BottleTransactionEntity> findBottleTransactionEntitiesByCustomerId(UUID customerId);
    List<BottleTransactionEntity> findByCustomerIdAndCreatedAtBetween(UUID customerId, LocalDateTime start, LocalDateTime end);
    Optional<BottleTransactionEntity> findByQrCodeId(UUID qrCode);

    @Query(value = """
            SELECT customer_id AS customerId, SUM(earned_points) AS totalPoints
            FROM bottle_transactions
            WHERE created_at >= :startDate
            GROUP BY customer_id
            ORDER BY totalPoints DESC
            LIMIT 5
    """, nativeQuery = true)
    List<TopCustomerDto> findTop5CustomersSince(@Param("startDate") LocalDateTime startDate);

    @Query(value = """
    SELECT 
        (bt.created_at AT TIME ZONE 'UTC' AT TIME ZONE :clientTimeZone)::date AS date,
        COALESCE(SUM(bt.earned_points), 0) AS totalPoints
    FROM bottle_transactions bt
    WHERE bt.customer_id = :customerId
      AND bt.created_at >= :startOfWeek
      AND bt.created_at <= :endOfToday
    GROUP BY date
    ORDER BY date
    """, nativeQuery = true)
    List<DailyPointProjectionDto> findWeeklyPointsByCustomer(UUID customerId, LocalDateTime startOfWeek,
                                                             LocalDateTime endOfToday,
                                                             String clientTimeZone);


    @Query(value = """
    SELECT 
        (bt.created_at AT TIME ZONE 'UTC' AT TIME ZONE :clientTimeZone)::date AS date,
        COALESCE(SUM(bt.number_of_small_bottles), 0) AS totalSmallBottles,
        COALESCE(SUM(bt.number_of_medium_bottles), 0) AS totalMediumBottles,
        COALESCE(SUM(bt.number_of_large_bottles), 0) AS totalLargeBottles,
        COALESCE(SUM(bt.earned_points), 0) AS totalPoints,
        COUNT(DISTINCT bt.customer_id) AS totalCustomers
    FROM bottle_transactions bt
    WHERE bt.created_at >= :startOfWeek
      AND bt.created_at <= :endOfToday
    GROUP BY date
    ORDER BY date
    """, nativeQuery = true)
    List<DailyBottleStatsProjectionDto> findWeeklyBottleStats(
            LocalDateTime startOfWeek,
            LocalDateTime endOfToday,
            String clientTimeZone
    );
}
