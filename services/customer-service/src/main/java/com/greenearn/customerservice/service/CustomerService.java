package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.CreateCustomerRequestDto;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.entity.CustomerPointEntity;
import com.greenearn.customerservice.mapper.CustomerMapper;
import com.greenearn.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

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

    private CustomerPointEntity initializeCustomerPointEntity() {
        CustomerPointEntity customerPointEntity = new CustomerPointEntity();
        customerPointEntity.setTotalNumberOfSmallBottles(0);
        customerPointEntity.setTotalNumberOfMediumBottles(0);
        customerPointEntity.setTotalNumberOfLargeBottles(0);
        customerPointEntity.setTotalPoints(0);
        return customerPointEntity;
    }
}
