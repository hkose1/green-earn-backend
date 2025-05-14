package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import com.greenearn.customerservice.enums.BottleTransactionStatus;
import com.greenearn.customerservice.mapper.BottleTransactionMapper;
import com.greenearn.customerservice.repository.BottleTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class BottleTransactionService {

    private final BottleTransactionRepository bottleTransactionRepository;
    private final CustomerService customerService;
    private final BottleTransactionMapper bottleTransactionMapper;


    public void createBottleTransaction(BottleTransactionRequestDto bottleTransactionRequestDto) {
        BottleTransactionEntity bottleTransactionEntity = bottleTransactionMapper.mapTransactionRequestDtoToEntity(bottleTransactionRequestDto);
        try {
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.SUCCESS);
            bottleTransactionRepository.save(bottleTransactionEntity);
            customerService.updateCustomerPointsOnBottleTransaction(bottleTransactionRequestDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.FAILED);
            bottleTransactionRepository.save(bottleTransactionEntity);
        }

    }
}
