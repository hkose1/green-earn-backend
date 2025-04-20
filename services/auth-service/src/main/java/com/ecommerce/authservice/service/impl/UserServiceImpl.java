package com.ecommerce.authservice.service.impl;

import com.ecommerce.authservice.dto.UserDto;
import com.ecommerce.authservice.entity.UserEntity;
import com.ecommerce.authservice.mapper.UserMapper;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(String id) {
        return userRepository.findById(UUID.fromString(id))
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Override
    @Transactional
    public void updateUserStatus(String id, boolean enabled) {
        UserEntity user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        if (!userRepository.existsById(UUID.fromString(id))) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(UUID.fromString(id));
    }
} 