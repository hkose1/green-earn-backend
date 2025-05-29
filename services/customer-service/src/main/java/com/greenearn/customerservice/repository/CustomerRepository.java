package com.greenearn.customerservice.repository;

import com.greenearn.customerservice.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findCustomerEntityByUserId(UUID customerId);

    List<CustomerEntity> findCustomerEntitiesByUserIdIs(List<UUID> userIds);
}
