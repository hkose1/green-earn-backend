package com.ecommerce.authservice.event;

import com.ecommerce.authservice.entity.UserEntity;
import com.ecommerce.authservice.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
