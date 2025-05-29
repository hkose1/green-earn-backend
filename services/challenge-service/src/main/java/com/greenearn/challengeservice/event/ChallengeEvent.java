package com.greenearn.challengeservice.event;


import com.greenearn.challengeservice.client.response.UserDto;
import com.greenearn.challengeservice.entity.ChallengeEntity;
import com.greenearn.challengeservice.enums.ChallengeEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ChallengeEvent {
    private UserDto userDto;
    private ChallengeEntity challengeEntity;
    private ChallengeEventType eventType;
    private Map<?, ?> data;
}
