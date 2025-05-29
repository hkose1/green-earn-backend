package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.entity.BottleEntity;
import com.greenearn.customerservice.enums.BottleSizeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BottleRepository extends JpaRepository<BottleEntity, UUID> {
    Optional<BottleEntity> findByBottleSize(BottleSizeType bottleSize);
}
