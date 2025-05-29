package com.greenearn.challengeservice.client;

import com.greenearn.challengeservice.client.response.ApiResponse;
import com.greenearn.challengeservice.client.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @GetMapping("/api/auth/current-user")
    ResponseEntity<ApiResponse<UserDto>> getCurrentUser(@RequestHeader("Authorization") String bearerToken);

}
