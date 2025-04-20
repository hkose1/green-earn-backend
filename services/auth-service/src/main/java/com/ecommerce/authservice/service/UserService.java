package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.UserDto;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(String id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    void updateUserStatus(String id, boolean enabled);
    void deleteUser(String id);
} 