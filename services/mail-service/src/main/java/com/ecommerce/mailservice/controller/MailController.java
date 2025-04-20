package com.ecommerce.mailservice.controller;


import com.ecommerce.mailservice.dto.SendCodeMailDto;
import com.ecommerce.mailservice.dto.SendMailDto;
import com.ecommerce.mailservice.dto.SendTokenMailDto;
import com.ecommerce.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send/account-verification/link")
    public void sendLinkAccountVerificationMail(@RequestBody SendTokenMailDto sendMailDto) {
        mailService.sendLinkAccountVerificationMail(sendMailDto);
    }

    @PostMapping("/send/account-verification/code")
    public void sendCodeAccountVerificationMail(@RequestBody SendCodeMailDto sendMailDto) {
        mailService.sendCodeAccountVerificationMail(sendMailDto);
    }
}
