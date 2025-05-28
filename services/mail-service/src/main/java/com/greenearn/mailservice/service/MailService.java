package com.greenearn.mailservice.service;

import com.greenearn.mailservice.dto.SendCodeMailDto;
import com.greenearn.mailservice.dto.SendTokenMailDto;

public interface MailService {

    void sendLinkAccountVerificationMail(SendTokenMailDto sendMailDto);

    void sendPasswordResetEmail(String name, String to, String token);

    void sendCodeAccountVerificationMail(SendCodeMailDto sendMailDto);

    void sendCodeResetPasswordMail(SendCodeMailDto sendMailDto);
}
