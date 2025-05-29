package com.greenearn.challengeservice.controller;

import com.greenearn.challengeservice.dto.*;
import com.greenearn.challengeservice.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
@Slf4j
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/me/{challengeId}/progress")
    public ResponseEntity<ProgressOnChallengeResponseDto> getProgressOnChallenge(@PathVariable("challengeId") UUID challengeId,
                                                                                 Authentication authentication) {
        return ResponseEntity.ok(challengeService.getProgressOnChallenge(challengeId, authentication));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeRequestDto createChallengeRequestDto) {
        challengeService.createChallenge(createChallengeRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{challengeId}")
    public ResponseEntity<Void> updateChallenge(@PathVariable("challengeId") UUID challengeId ,
                                                @RequestBody UpdateChallengeRequestDto updateChallengeRequestDto) {
        challengeService.updateChallenge(challengeId, updateChallengeRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<ChallengeResponseAdminDto>> getAllChallengesAdmin() {
        return ResponseEntity.ok(challengeService.getAllChallengesForAdmin());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{challengeId}")
    public ResponseEntity<ChallengeResponseAdminDto> getChallengeByIdAdmin(@PathVariable("challengeId") UUID challengeId) {
        return ResponseEntity.ok(challengeService.getChallengeByIdAdmin(challengeId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(Authentication authentication,
                                          @RequestBody SubscribeToChallengeRequestDto subscribeRequestDto) {
        challengeService.subscribe(authentication, subscribeRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponseDto>> getAllChallenges() {
        return ResponseEntity.ok(challengeService.getAllChallenges());
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeResponseDto> getChallengeById(@PathVariable("challengeId") UUID challengeId) {
        return ResponseEntity.ok(challengeService.getChallengeById(challengeId));
    }

}
