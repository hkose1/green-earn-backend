package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.dto.CreateCustomerRequestDto;
import com.greenearn.customerservice.dto.CustomerResponseDto;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.entity.CustomerPointEntity;
import com.greenearn.customerservice.mapper.CustomerMapper;
import com.greenearn.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CurrentCustomerService currentCustomerService;

    public void createCustomer(CreateCustomerRequestDto createCustomerRequestDto) {
        Optional<CustomerEntity> customer = customerRepository
                .findCustomerEntityByUserId(createCustomerRequestDto.getUserId());
        if (!customer.isPresent()) {
            CustomerEntity customerEntity = customerMapper.mapCreateDtoToDomain(createCustomerRequestDto);
            CustomerPointEntity customerPoint = initializeCustomerPointEntity();
            customerPoint.setCustomer(customerEntity);
            customerEntity.setCustomerPoint(customerPoint);;
            customerRepository.save(customerEntity);
        } else {
            throw new RuntimeException("Customer already exists with user id: " + createCustomerRequestDto.getUserId());
        }
    }

    @Transactional
    public void updateCustomerPointsOnBottleTransaction(BottleTransactionRequestDto bottleTransactionRequestDto, Authentication authentication) {
        final UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new RuntimeException("Customer does not exist with user id: " + customerId);
        }
        CustomerPointEntity customerPoint = getCustomerPointEntity(bottleTransactionRequestDto, customer);
        customer.get().setCustomerPoint(customerPoint);
        customerRepository.save(customer.get());
    }

    private static CustomerPointEntity getCustomerPointEntity(BottleTransactionRequestDto bottleTransactionRequestDto, Optional<CustomerEntity> customer) {
        CustomerPointEntity customerPoint = customer.get().getCustomerPoint();
        customerPoint.setTotalPoints(customerPoint.getTotalPoints() + bottleTransactionRequestDto.getPoints());
        customerPoint.setTotalNumberOfSmallBottles(customerPoint.getTotalNumberOfSmallBottles() + bottleTransactionRequestDto.getNumberOfSmallBottles());
        customerPoint.setTotalNumberOfMediumBottles(customerPoint.getTotalNumberOfMediumBottles() + bottleTransactionRequestDto.getNumberOfMediumBottles());
        customerPoint.setTotalNumberOfLargeBottles(customerPoint.getTotalNumberOfLargeBottles() + bottleTransactionRequestDto.getNumberOfLargeBottles());
        return customerPoint;
    }


    private CustomerPointEntity initializeCustomerPointEntity() {
        CustomerPointEntity customerPointEntity = new CustomerPointEntity();
        customerPointEntity.setTotalNumberOfSmallBottles(0);
        customerPointEntity.setTotalNumberOfMediumBottles(0);
        customerPointEntity.setTotalNumberOfLargeBottles(0);
        customerPointEntity.setTotalPoints(0);
        return customerPointEntity;
    }

    public List<CustomerResponseDto> getAllCustomers() {
        try {
            return customerRepository.findAll()
                    .stream()
                    .map(customerEntity -> customerMapper.map2ResponseDto(customerEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all customers", e);
            return new ArrayList<>();
        }
    }

    public CustomerResponseDto getCustomerById(UUID id) {
        return customerMapper.map2ResponseDto(
                customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id))
        );
    }
}
