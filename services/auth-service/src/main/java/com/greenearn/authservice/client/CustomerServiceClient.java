package com.greenearn.authservice.client;

import com.greenearn.authservice.client.request.CreateCustomerRequestDto;
import com.greenearn.authservice.client.request.UpdateCustomerRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @PostMapping("/api/customers/internal")
    ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerRequestDto customerRequestDto,
                                        @RequestHeader("Authorization") String bearerToken);

    @PutMapping("/api/customers/internal")
    ResponseEntity<Void> updateCustomer(@RequestBody UpdateCustomerRequestDto customerRequestDto,
                                        @RequestHeader("Authorization") String bearerToken);
}
