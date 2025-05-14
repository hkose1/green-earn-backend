package com.greenearn.authservice.mapper;

import com.greenearn.authservice.client.request.CreateCustomerRequestDto;
import org.springframework.stereotype.Component;

import com.greenearn.authservice.dto.UserDto;
import com.greenearn.authservice.entity.UserEntity;

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

    public CreateCustomerRequestDto mapEntityToCreateCustomerRequestDto(UserEntity entity) {
        if (entity == null) return null;
        return CreateCustomerRequestDto.builder()
                .userId(entity.getId())
                .firstName(entity.getFirstname())
                .lastName(entity.getLastname())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .build();
    }
} 