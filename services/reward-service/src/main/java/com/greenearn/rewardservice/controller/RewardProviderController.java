package com.greenearn.rewardservice.controller;

import com.greenearn.rewardservice.dto.CreateRewardProviderRequestDto;
import com.greenearn.rewardservice.dto.RewardProviderResponseDto;
import com.greenearn.rewardservice.service.RewardProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reward-providers")
@Slf4j
public class RewardProviderController {

    private final RewardProviderService rewardProviderService;

    @PostMapping
    public ResponseEntity<Void> createRewardProvider(@RequestBody @Valid CreateRewardProviderRequestDto requestDto) {
        rewardProviderService.createRewardProvider(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RewardProviderResponseDto>> getAllRewards() {
        return ResponseEntity.ok(rewardProviderService.getAllRewardProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardProviderResponseDto> getRewardById(@PathVariable UUID id) {
        return ResponseEntity.ok(rewardProviderService.getRewardProviderById(id));
    }
}
