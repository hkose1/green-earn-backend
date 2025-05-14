package com.greenearn.customerservice.mapper;

import com.greenearn.customerservice.dto.CreateCustomerRequestDto;
import com.greenearn.customerservice.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity mapCreateDtoToDomain(CreateCustomerRequestDto createCustomerRequestDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUserId(createCustomerRequestDto.getUserId());
        customerEntity.setFirstName(createCustomerRequestDto.getFirstName());
        customerEntity.setLastName(createCustomerRequestDto.getLastName());
        customerEntity.setEmail(createCustomerRequestDto.getEmail());
        customerEntity.setCreatedAt(createCustomerRequestDto.getCreatedAt());
        customerEntity.setUpdatedAt(createCustomerRequestDto.getCreatedAt());
        return customerEntity;
    }
}
