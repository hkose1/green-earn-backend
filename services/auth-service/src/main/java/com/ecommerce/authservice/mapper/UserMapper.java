package com.ecommerce.authservice.mapper;

import com.ecommerce.authservice.dto.UserDto;
import com.ecommerce.authservice.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        
        return UserDto.builder()
                .id(entity.getId().toString())
                .username(entity.getUsername())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .role(entity.getRole() != null ? entity.getRole().getRole().name() : null)
                .enabled(entity.getEnabled())
                .build();
    }

    public List<UserDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) return null;
        
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
} 