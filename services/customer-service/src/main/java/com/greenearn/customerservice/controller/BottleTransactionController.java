package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.service.BottleTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bottle-transactions")
@Slf4j
public class BottleTransactionController {

    private final BottleTransactionService bottleTransactionService;

    @PostMapping
    public ResponseEntity<Void> createBottleTransaction(@RequestBody @Validated BottleTransactionRequestDto bottleTransactionRequestDto) {
        bottleTransactionService.createBottleTransaction(bottleTransactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
