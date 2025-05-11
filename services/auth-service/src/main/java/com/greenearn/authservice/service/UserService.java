package com.greenearn.authservice.service;

import java.util.List;

import com.greenearn.authservice.dto.UserDto;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(String id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    void updateUserStatus(String id, boolean enabled);
    void deleteUser(String id);
} 