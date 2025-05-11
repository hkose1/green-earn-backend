package com.greenearn.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.greenearn.authservice.entity.ConfirmationEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, UUID> {
    Optional<ConfirmationEntity> findByKey(String key);


    @Query(nativeQuery = true, value = "SELECT * FROM docdatabase.confirmations WHERE user_id LIKE :userId")
    Optional<ConfirmationEntity> findByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM docdatabase.confirmations WHERE user_id LIKE :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}
