package com.greenearn.rewardservice.client;

import com.greenearn.rewardservice.client.request.UpdateCustomerPointsRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/api/customers/internal/me/points")
    ResponseEntity<Integer> getMyPoints(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/api/customers/internal/me/points/update")
    ResponseEntity<Void> updateMyPoints(@RequestBody UpdateCustomerPointsRequestDto requestDto,
                                        @RequestHeader("Authorization") String bearerToken);
}
