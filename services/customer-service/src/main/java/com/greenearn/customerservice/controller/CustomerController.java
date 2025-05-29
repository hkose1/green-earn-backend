package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.client.request.ProgressInformationRequestDto;
import com.greenearn.customerservice.client.request.UpdatePointsAfterCompletedChallengeRequestDto;
import com.greenearn.customerservice.client.response.ProgressInformationResponseDto;
import com.greenearn.customerservice.client.response.UpdateCustomerPointsResponseDto;
import com.greenearn.customerservice.dto.*;
import com.greenearn.customerservice.service.BottleTransactionService;
import com.greenearn.customerservice.service.CurrentCustomerService;
import com.greenearn.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final CurrentCustomerService currentCustomerService;
    private final BottleTransactionService bottleTransactionService;

    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDto> getCurrentCustomer(Authentication authentication) {
        return ResponseEntity.ok(currentCustomerService.getCurrentCustomerAsResponseDto(authentication));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/search/by-user-ids")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomersByUserIds(@RequestBody List<UUID> userIds) {
        return ResponseEntity.ok(customerService.getAllCustomersByUserIds(userIds));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userId/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerByUserId(id));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<CustomerResponseDto> updateCustomerByUserId(
            @PathVariable("userId") UUID userId,
            @RequestBody @Valid UpdateCustomerRequestDto updateCustomerRequestDto) {
        return ResponseEntity.ok(customerService.updateCustomerByUserId(userId, updateCustomerRequestDto));
    }

    @Hidden
    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/internal")
    public ResponseEntity<Void> createCustomer(@RequestBody @Valid CreateCustomerRequestDto createCustomerRequestDto) {
        customerService.createCustomer(createCustomerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/me")
    public ResponseEntity<CustomerResponseDto> updateCurrentCustomer(
            Authentication authentication,
            @RequestBody @Valid UpdateCustomerRequestDto updateCustomerRequestDto) {
        return ResponseEntity.ok(customerService.updateCurrentCustomer(authentication, updateCustomerRequestDto));
    }

    @Hidden
    @GetMapping("/internal/me/points")
    public ResponseEntity<Integer> getMyPoints(Authentication authentication) {
        return ResponseEntity.ok(customerService.getMyPoints(authentication));
    }

    @Hidden
    @PostMapping("/internal/me/points/update")
    public ResponseEntity<Void> updateMyPoints(@RequestBody UpdateCustomerPointsResponseDto updateDto,
                                               Authentication authentication) {
        customerService.updateMyPoints(authentication, updateDto);
        return ResponseEntity.ok().build();
    }

    @Hidden
    @PostMapping("/internal/me/challenge-completed/points/update")
    public ResponseEntity<Void> updateMyPointsAfterCompleteChallenge(@RequestBody UpdatePointsAfterCompletedChallengeRequestDto updateDto,
                                               Authentication authentication) {
        customerService.updateMyPointsAfterCompleteChallenge(authentication, updateDto);
        return ResponseEntity.ok().build();
    }

    @Hidden
    @PostMapping("/internal/me/progress-on-challenge")
    public ResponseEntity<ProgressInformationResponseDto> getMyProgressOnChallenge(@RequestBody ProgressInformationRequestDto requestDto,
                                                                                   Authentication authentication) {
        return ResponseEntity.ok(customerService.getMyProgressOnChallenge(requestDto, authentication));
    }

    @Hidden
    @PreAuthorize("hasRole('SYSTEM')")
    @PutMapping("/internal")
    public ResponseEntity<Void> internalUpdateCurrentCustomer(
            Authentication authentication,
            @RequestBody @Valid InternalUpdateCustomerRequestDto internalUpdateCustomerRequestDto
    ) {
        customerService.internalUpdateCurrentCustomer(authentication, internalUpdateCustomerRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/statistics/on-bottle-transactions/top-customers")
    public ResponseEntity<List<PublicCustomerResponseDto>> getTopCustomersOnBottleTransactions() {
        return ResponseEntity.ok(bottleTransactionService.getTop5CustomersLastMonth());
    }

    @GetMapping("/statistics/on-bottle-transactions/current-week")
    public ResponseEntity<Map<LocalDate, Integer>> getWeeklyPointsByCustomer(Authentication authentication,
                                                                             @RequestParam(name = "timezone", defaultValue = "UTC") String clientTimeZone) {
        return ResponseEntity.ok(bottleTransactionService.getWeeklyPointsByCustomer(authentication, clientTimeZone));
    }
}
