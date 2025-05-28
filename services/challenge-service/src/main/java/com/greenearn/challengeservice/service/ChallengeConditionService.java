package com.greenearn.challengeservice.service;


import com.greenearn.challengeservice.dto.ChallengeConditionDto;
import com.greenearn.challengeservice.mapper.ChallengeConditionMapper;
import com.greenearn.challengeservice.repository.ChallengeConditionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class ChallengeConditionService {

    private final ChallengeConditionRepository challengeConditionRepository;
    private final ChallengeConditionMapper challengeConditionMapper;

    public ChallengeConditionDto getChallengeConditionByChallengeId(UUID challengeId) {
        return challengeConditionMapper.map2Dto(
                challengeConditionRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge condition not found with challenge id: " + challengeId))
        );
    }
}
