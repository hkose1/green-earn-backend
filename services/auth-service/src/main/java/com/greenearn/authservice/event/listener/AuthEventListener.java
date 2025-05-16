package com.greenearn.authservice.event.listener;

import com.greenearn.authservice.client.CustomerServiceClient;
import com.greenearn.authservice.client.request.CreateCustomerRequestDto;
import com.greenearn.authservice.client.request.UpdateCustomerRequestDto;
import com.greenearn.authservice.mapper.UserMapper;
import com.greenearn.authservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.greenearn.authservice.client.MailServiceClient;
import com.greenearn.authservice.dto.SendCodeMailDto;
import com.greenearn.authservice.dto.SendTokenMailDto;
import com.greenearn.authservice.event.UserEvent;

@Component
@RequiredArgsConstructor
public class AuthEventListener {

    private final MailServiceClient mailServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @EventListener
    public void onUserEvent(UserEvent event) {
        switch (event.getType()) {
            case REGISTRATION -> {
                SendTokenMailDto mailDto = SendTokenMailDto.builder()
                        .name(event.getUser().getUsername())
                        .to(event.getUser().getEmail())
                        .token((String) event.getData().get("key"))
                        .build();
                mailServiceClient.sendLinkAccountVerificationMail(mailDto);
                SendCodeMailDto codeMailDto = SendCodeMailDto.builder()
                        .name(event.getUser().getUsername())
                        .to(event.getUser().getEmail())
                        .code((String) event.getData().get("code"))
                        .build();
                mailServiceClient.sendCodeAccountVerificationMail(codeMailDto);
            }
            case CREATE_CUSTOMER -> {
                CreateCustomerRequestDto customerRequestDto = userMapper
                        .mapEntityToCreateCustomerRequestDto(event.getUser());
                final String token = jwtTokenProvider.generateSystemToken();
                customerServiceClient.createCustomer(customerRequestDto, "Bearer " + token);
            }
            case UPDATE_CUSTOMER -> {
                UpdateCustomerRequestDto updateCustomerRequestDto = UpdateCustomerRequestDto.builder()
                        .userId(event.getUser().getId())
                        .firstName(event.getUser().getFirstname())
                        .lastName(event.getUser().getLastname())
                        .build();
                final String token = jwtTokenProvider.generateSystemToken();
                customerServiceClient.updateCustomer(updateCustomerRequestDto, "Bearer " + token);
            }
            default -> {}
        }
    }
}
