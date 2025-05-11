package com.greenearn.authservice.event.listener;

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
            default -> {}
        }
    }
}
