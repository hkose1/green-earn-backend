package com.greenearn.rewardservice.service;

import com.greenearn.rewardservice.dto.RewardTransactionResponseDto;
import com.greenearn.rewardservice.mapper.RewardTransactionMapper;
import com.greenearn.rewardservice.repository.RewardTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class RewardTransactionService {

    private final RewardTransactionRepository rewardTransactionRepository;

    public List<RewardTransactionResponseDto> getALlRewardTransactions() {
        return rewardTransactionRepository.findAll().stream()
                .map(rewardTransaction -> RewardTransactionMapper.map2ResponseDto(rewardTransaction))
                .toList();
    }

    public RewardTransactionResponseDto getRewardTransactionById(UUID id) {
        return RewardTransactionMapper.map2ResponseDto(
                rewardTransactionRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("Could not find reward transaction with id: " + id)
                )
        );
    }

    public List<RewardTransactionResponseDto> getMyAllRewardTransactions(Authentication authentication) {
        UUID userId = UtilService.getLoggedInUserId(authentication);
        return rewardTransactionRepository.findAllByUserId(userId).stream()
                .map(rewardTransaction -> RewardTransactionMapper.map2ResponseDto(rewardTransaction))
                .toList();
    }
}
