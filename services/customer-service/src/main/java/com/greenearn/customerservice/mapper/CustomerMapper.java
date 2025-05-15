package com.greenearn.customerservice.mapper;

import com.greenearn.customerservice.dto.CreateCustomerRequestDto;
import com.greenearn.customerservice.dto.CustomerPointDto;
import com.greenearn.customerservice.dto.CustomerResponseDto;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.entity.CustomerPointEntity;
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

    public CustomerPointDto mapCustomerPointToDto(CustomerPointEntity customerPointEntity) {
        return CustomerPointDto.builder()
                .totalPoints(customerPointEntity.getTotalPoints())
                .totalNumberOfSmallBottles(customerPointEntity.getTotalNumberOfSmallBottles())
                .totalNumberOfMediumBottles(customerPointEntity.getTotalNumberOfMediumBottles())
                .totalNumberOfLargeBottles(customerPointEntity.getTotalNumberOfLargeBottles())
                .build();
    }

    public CustomerResponseDto map2ResponseDto(CustomerEntity customerEntity) {
        return CustomerResponseDto.builder()
                .id(customerEntity.getId())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .email(customerEntity.getEmail())
                .createdAt(customerEntity.getCreatedAt())
                .updatedAt(customerEntity.getUpdatedAt())
                .phoneNumber(customerEntity.getPhoneNumber())
                .profileImageUrl(customerEntity.getProfileImageUrl())
                .userId(customerEntity.getUserId())
                .customerPointDto(mapCustomerPointToDto(customerEntity.getCustomerPoint()))
                .build();
    }
}
