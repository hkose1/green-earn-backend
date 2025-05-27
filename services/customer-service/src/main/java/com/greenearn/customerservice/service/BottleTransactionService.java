package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.dto.BottleTransactionResponseDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.enums.BottleTransactionStatus;
import com.greenearn.customerservice.mapper.BottleTransactionMapper;
import com.greenearn.customerservice.repository.BottleTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class BottleTransactionService {

    private final BottleTransactionRepository bottleTransactionRepository;
    private final CustomerService customerService;
    private final CurrentCustomerService currentCustomerService;
    private final BottleTransactionMapper bottleTransactionMapper;


    public void createBottleTransaction(BottleTransactionRequestDto bottleTransactionRequestDto, Authentication authentication) {
        BottleTransactionEntity bottleTransactionEntity = bottleTransactionMapper.mapTransactionRequestDtoToEntity(bottleTransactionRequestDto, authentication);
        try {
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.SUCCESS);
            bottleTransactionRepository.save(bottleTransactionEntity);
            customerService.updateCustomerPointsOnBottleTransaction(bottleTransactionRequestDto, authentication);
        } catch (Exception e) {
            log.error(e.getMessage());
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.FAILED);
            bottleTransactionRepository.save(bottleTransactionEntity);
        }
    }

    public List<BottleTransactionResponseDto> getAllBottleTransactions() {
        try {
            return bottleTransactionRepository.findAll()
                    .stream()
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions", e);
            return new ArrayList<>();
        }
    }

    public BottleTransactionResponseDto getBottleTransactionById(UUID id) {
        return bottleTransactionMapper.map2ResponseDto(
                bottleTransactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Bottle transaction not found with id: " + id))
        );
    }

    public List<BottleTransactionResponseDto> getAllBottleTransactionsByCustomerId(UUID customerId) {
        try {
            return bottleTransactionRepository.findBottleTransactionEntitiesByCustomerId(customerId)
                    .stream()
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions by customer id", e);
            return new ArrayList<>();
        }
    }


    public List<BottleTransactionResponseDto> getMyAllBottleTransactions(Authentication authentication) {
        UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);
        try {
            return bottleTransactionRepository.findBottleTransactionEntitiesByCustomerId(customerId)
                    .stream()
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions by customer id", e);
            return new ArrayList<>();
        }
    }

    public BottleTransactionResponseDto getMyBottleTransactionById(Authentication authentication, UUID id) {
        UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);
        BottleTransactionEntity bottleTransactionEntity = bottleTransactionRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Bottle transaction not found with id: " + id));
        if (!Objects.equals(customerId, bottleTransactionEntity.getCustomerId())) {
            throw new AccessDeniedException("You do not have permission to access this bottle transaction.");
        }
        return bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity);
    }
}
