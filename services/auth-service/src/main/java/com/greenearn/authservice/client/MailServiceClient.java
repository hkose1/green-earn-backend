package com.greenearn.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.greenearn.authservice.dto.SendCodeMailDto;
import com.greenearn.authservice.dto.SendTokenMailDto;

@FeignClient(name = "mail-service")
public interface MailServiceClient {

    @PostMapping("/api/mail/send/account-verification/link")
    void sendLinkAccountVerificationMail(@RequestBody SendTokenMailDto sendTokenMailDto);

    @PostMapping("/api/mail/send/account-verification/code")
    void sendCodeAccountVerificationMail(@RequestBody SendCodeMailDto sendCodeMailDto);
}
