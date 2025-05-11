package com.greenearn.authservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import com.greenearn.authservice.entity.UserEntity;
import com.greenearn.authservice.enums.EventType;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
