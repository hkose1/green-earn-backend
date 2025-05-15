package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.CreateCustomerRequestDto;
import com.greenearn.customerservice.dto.CustomerResponseDto;
import com.greenearn.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDto> getCurrentCustomer(Authentication authentication) {
        return ResponseEntity.ok(customerService.getCurrentCustomer(authentication));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Hidden
    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody @Validated CreateCustomerRequestDto createCustomerRequestDto) {
        customerService.createCustomer(createCustomerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
