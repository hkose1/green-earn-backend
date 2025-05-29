package com.greenearn.mailservice.controller;


import com.greenearn.mailservice.dto.SendChallengeCompletedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenearn.mailservice.dto.SendCodeMailDto;
import com.greenearn.mailservice.dto.SendMailDto;
import com.greenearn.mailservice.dto.SendTokenMailDto;
import com.greenearn.mailservice.service.MailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send/challenge-completed")
    public void sendChallengeCompletedMail(@RequestBody SendChallengeCompletedDto sendChallengeCompletedDto) {
        mailService.sendChallengeCompletedMail(sendChallengeCompletedDto);
    }

    @PostMapping("/send/account-verification/link")
    public void sendLinkAccountVerificationMail(@RequestBody SendTokenMailDto sendMailDto) {
        mailService.sendLinkAccountVerificationMail(sendMailDto);
    }

    @PostMapping("/send/account-verification/code")
    public void sendCodeAccountVerificationMail(@RequestBody SendCodeMailDto sendMailDto) {
        mailService.sendCodeAccountVerificationMail(sendMailDto);
    }

    @PostMapping("/send/reset-password/code")
    public void sendCodeResetPasswordMail(@RequestBody SendCodeMailDto sendMailDto) {
        mailService.sendCodeResetPasswordMail(sendMailDto);
    }
}
