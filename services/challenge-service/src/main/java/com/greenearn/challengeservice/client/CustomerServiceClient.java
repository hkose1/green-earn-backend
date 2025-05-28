package com.greenearn.challengeservice.client;

import com.greenearn.challengeservice.client.request.ProgressInformationRequestDto;
import com.greenearn.challengeservice.client.response.ProgressInformationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @PostMapping("/api/customers/internal/me/progress-on-challenge")
    ResponseEntity<ProgressInformationResponseDto> getProgressOnChallenge(@RequestBody ProgressInformationRequestDto progressInformationRequestDto,
                                                                          @RequestHeader("Authorization") String bearerToken);
}
