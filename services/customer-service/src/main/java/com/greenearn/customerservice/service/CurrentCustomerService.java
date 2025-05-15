package com.greenearn.customerservice.service;

import com.greenearn.customerservice.dto.CustomerResponseDto;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.mapper.CustomerMapper;
import com.greenearn.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentCustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponseDto getCurrentCustomer(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("userId");
        UUID userUuid = UUID.fromString(userId);
        Optional<CustomerEntity> customer = customerRepository
                .findCustomerEntityByUserId(userUuid);
        if (!customer.isPresent()) {
            throw new RuntimeException("Customer does not exist with user id: " + userUuid);
        }
        return customerMapper.map2ResponseDto(customer.get());
    }

    public UUID getCurrentCustomerId(Authentication authentication) {
        CustomerResponseDto customerResponseDto = getCurrentCustomer(authentication);
        if (customerResponseDto == null) {
            return null;
        }
        return customerResponseDto.getId();
    }
}
