package com.greenearn.rewardservice.controller;

import com.greenearn.rewardservice.dto.RewardTransactionResponseDto;
import com.greenearn.rewardservice.service.RewardTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reward-transactions")
@Slf4j
public class RewardTransactionController {

    private final RewardTransactionService rewardTransactionService;

    @GetMapping("/me")
    public ResponseEntity<List<RewardTransactionResponseDto>> getMyAllRewardTransactions(Authentication authentication) {
        return ResponseEntity.ok(rewardTransactionService.getMyAllRewardTransactions(authentication));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RewardTransactionResponseDto>> getAllRewardTransactions() {
        return ResponseEntity.ok(rewardTransactionService.getALlRewardTransactions());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RewardTransactionResponseDto> getRewardTransactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(rewardTransactionService.getRewardTransactionById(id));
    }
}
