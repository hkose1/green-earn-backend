package com.greenearn.rewardservice.service;

import com.greenearn.rewardservice.dto.CreateRewardProviderRequestDto;
import com.greenearn.rewardservice.dto.RewardProviderResponseDto;
import com.greenearn.rewardservice.entity.RewardEntity;
import com.greenearn.rewardservice.entity.RewardProviderEntity;
import com.greenearn.rewardservice.mapper.RewardMapper;
import com.greenearn.rewardservice.mapper.RewardProviderMapper;
import com.greenearn.rewardservice.repository.RewardProviderRepository;
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
public class RewardProviderService {

    private final RewardProviderRepository rewardProviderRepository;

    public void createRewardProvider(CreateRewardProviderRequestDto requestDto) {
        try {
            RewardProviderEntity rewardProvider = RewardProviderMapper.mapCreateRewardRequestDto2Entity(requestDto);
            if (requestDto.getRewards() != null && !requestDto.getRewards().isEmpty()) {
                List<RewardEntity> rewards = requestDto.getRewards().stream()
                        .map(rewardDto -> {
                            RewardEntity reward = RewardMapper.mapCreateRewardWithoutProviderDtoToEntity(rewardDto);
                            reward.setRewardProvider(rewardProvider);
                            return reward;
                        }).toList();

                rewardProvider.setRewards(rewards);
            }
            rewardProviderRepository.save(rewardProvider);
        } catch (Exception e) {
            log.error("Error creating reward provider", e);
        }
    }

    public List<RewardProviderResponseDto> getAllRewardProviders() {
        return rewardProviderRepository.findAll().stream()
                .map(rewardProvider -> RewardProviderMapper.map2ResponseDto(rewardProvider))
                .toList();
    }

    public RewardProviderResponseDto getRewardProviderById(UUID id) {
        return RewardProviderMapper.map2ResponseDto(
                rewardProviderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reward provider not found with id: " + id))
        );
    }
}
