package com.greenearn.rewardservice.service;

import com.greenearn.rewardservice.dto.CreateRewardRequestDto;
import com.greenearn.rewardservice.dto.RewardResponseDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import com.greenearn.rewardservice.mapper.RewardMapper;
import com.greenearn.rewardservice.repository.RewardProviderRepository;
import com.greenearn.rewardservice.repository.RewardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private final RewardRepository rewardRepository;
    private final RewardProviderRepository rewardProviderRepository;

    public void createReward(CreateRewardRequestDto dto) {
        RewardProviderEntity provider = rewardProviderRepository.findById(dto.getRewardProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Reward provider not found"));

        RewardEntity reward = RewardMapper.mapCreateRequestDto2Entity(dto, provider);
        rewardRepository.save(reward);
    }

    public List<RewardResponseDto> getAllRewards() {
        return rewardRepository.findAll().stream()
                .map(rewardEntity -> RewardMapper.map2ResponseDto(rewardEntity))
                .toList();
    }

    public RewardResponseDto getRewardById(UUID id) {
        return RewardMapper.map2ResponseDto(
                rewardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reward not found with id: " + id))
        );
    }
}
