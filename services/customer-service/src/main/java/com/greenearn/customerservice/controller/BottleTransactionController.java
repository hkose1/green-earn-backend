package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.dto.BottleTransactionResponseDto;
import com.greenearn.customerservice.service.BottleTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bottle-transactions")
@Slf4j
public class BottleTransactionController {

    private final BottleTransactionService bottleTransactionService;

    @GetMapping
    public ResponseEntity<List<BottleTransactionResponseDto>> getAllBottleTransactions() {
        return ResponseEntity.ok(bottleTransactionService.getAllBottleTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BottleTransactionResponseDto> getBottleTransactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(bottleTransactionService.getBottleTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBottleTransaction(@RequestBody @Validated BottleTransactionRequestDto bottleTransactionRequestDto) {
        bottleTransactionService.createBottleTransaction(bottleTransactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
