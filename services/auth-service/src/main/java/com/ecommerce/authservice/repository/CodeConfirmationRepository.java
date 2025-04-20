package com.ecommerce.authservice.repository;

import com.ecommerce.authservice.entity.CodeConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface CodeConfirmationRepository extends JpaRepository<CodeConfirmationEntity, UUID>  {

    Optional<CodeConfirmationEntity> findByCode(String code);


    @Query(nativeQuery = true, value = "SELECT * FROM docdatabase.code_confirmations WHERE user_id LIKE :userId")
    Optional<CodeConfirmationEntity> findByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM docdatabase.code_confirmations WHERE user_id LIKE :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}
