package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.dto.projection.DailyBottleStatsProjectionDto;
import com.greenearn.customerservice.dto.projection.DailyPointProjectionDto;
import com.greenearn.customerservice.dto.projection.MonthlyStatsDto;
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

    @Query(value = """
    WITH monthly_stats AS (
        SELECT 
            (SELECT COUNT(*) FROM customers) AS totalCustomers,
            -- Son bir ay içinde kayıt olan müşteri sayısı
            (SELECT COUNT(*) FROM customers 
             WHERE created_at >= :startOfMonth 
             AND created_at <= :endOfToday) AS newCustomers,
            COALESCE(SUM(CASE 
                WHEN bt.created_at >= :startOfMonth 
                AND bt.created_at <= :endOfToday 
                THEN bt.number_of_small_bottles 
                ELSE 0 
            END), 0) AS monthlySmallBottles,
            COALESCE(SUM(CASE 
                WHEN bt.created_at >= :startOfMonth 
                AND bt.created_at <= :endOfToday 
                THEN bt.number_of_medium_bottles 
                ELSE 0 
            END), 0) AS monthlyMediumBottles,
            COALESCE(SUM(CASE 
                WHEN bt.created_at >= :startOfMonth 
                AND bt.created_at <= :endOfToday 
                THEN bt.number_of_large_bottles 
                ELSE 0 
            END), 0) AS monthlyLargeBottles,
            COALESCE(SUM(CASE 
                WHEN bt.created_at >= :startOfMonth 
                AND bt.created_at <= :endOfToday 
                THEN bt.earned_points 
                ELSE 0 
            END), 0) AS monthlyPoints
        FROM bottle_transactions bt
    ),
    previous_stats AS (
        SELECT 
            COALESCE(SUM(bt.number_of_small_bottles), 0) AS totalSmallBottles,
            COALESCE(SUM(bt.number_of_medium_bottles), 0) AS totalMediumBottles,
            COALESCE(SUM(bt.number_of_large_bottles), 0) AS totalLargeBottles,
            COALESCE(SUM(bt.earned_points), 0) AS totalPoints
        FROM bottle_transactions bt
        WHERE bt.created_at < :startOfMonth
          AND bt.bottle_transaction_status = 'SUCCESS'
    )
    SELECT 
        ms.totalCustomers,
        ms.newCustomers,
        ms.monthlySmallBottles,
        ms.monthlyMediumBottles,
        ms.monthlyLargeBottles,
        ms.monthlyPoints,
        ps.totalSmallBottles,
        ps.totalMediumBottles,
        ps.totalLargeBottles,
        ps.totalPoints
    FROM monthly_stats ms
    CROSS JOIN previous_stats ps
    """, nativeQuery = true)
    MonthlyStatsDto findMonthlyStats(
            LocalDateTime startOfMonth,
            LocalDateTime endOfToday
    );

}
