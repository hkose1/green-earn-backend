package com.greenearn.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.greenearn.authservice.entity.CodeConfirmationEntity;

import java.util.Optional;
import java.util.UUID;

public interface CodeConfirmationRepository extends JpaRepository<CodeConfirmationEntity, UUID>  {

    Optional<CodeConfirmationEntity> findByCode(String code);
    Optional<CodeConfirmationEntity> findByUserEntityId(UUID userEntityId);
    @Transactional
    void deleteByUserEntityId(UUID userEntityId);
}
