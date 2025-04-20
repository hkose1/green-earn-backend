package com.ecommerce.authservice.client;

import com.ecommerce.authservice.dto.SendCodeMailDto;
import com.ecommerce.authservice.dto.SendTokenMailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailServiceClient {

    @PostMapping("/api/mail/send/account-verification/link")
    void sendLinkAccountVerificationMail(@RequestBody SendTokenMailDto sendTokenMailDto);

    @PostMapping("/api/mail/send/account-verification/code")
    void sendCodeAccountVerificationMail(@RequestBody SendCodeMailDto sendCodeMailDto);
}
