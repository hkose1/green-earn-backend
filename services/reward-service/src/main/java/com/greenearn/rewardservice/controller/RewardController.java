package com.greenearn.rewardservice.controller;

import com.greenearn.rewardservice.dto.CreateRewardRequestDto;
import com.greenearn.rewardservice.dto.RewardResponseDto;
import com.greenearn.rewardservice.service.RewardService;
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
@RequestMapping("/api/rewards")
@Slf4j
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    public ResponseEntity<Void> createReward(@RequestBody @Valid CreateRewardRequestDto requestDto) {
        rewardService.createReward(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RewardResponseDto>> getAllRewards() {
        return ResponseEntity.ok(rewardService.getAllRewards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardResponseDto> getRewardById(@PathVariable UUID id) {
        return ResponseEntity.ok(rewardService.getRewardById(id));
    }

}
