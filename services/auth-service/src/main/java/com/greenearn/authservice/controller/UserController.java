package com.greenearn.authservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.greenearn.authservice.dto.ApiResponse;
import com.greenearn.authservice.dto.UserDto;
import com.greenearn.authservice.service.UserService;
import com.greenearn.authservice.util.RequestUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers(HttpServletRequest request) {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, users, "", HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable String id,
                                                          HttpServletRequest request) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, user, "", HttpStatus.OK));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByEmail(@PathVariable String email,
                                                             HttpServletRequest request) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, user, "", HttpStatus.OK));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable String username,
                                                                HttpServletRequest request) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, user, "", HttpStatus.OK));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(@PathVariable String id,
                                                              @RequestParam boolean enabled,
                                                            HttpServletRequest request) {
        userService.updateUserStatus(id, enabled);
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, null, "User status updated successfully", HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id,
                                                      HttpServletRequest request) {
        userService.deleteUser(id);
        return ResponseEntity.ok()
                .body(RequestUtils.getApiResponse(request, null, "User deleted successfully", HttpStatus.OK));
    }
} 