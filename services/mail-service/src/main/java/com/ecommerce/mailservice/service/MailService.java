package com.ecommerce.mailservice.service;

import com.ecommerce.mailservice.dto.SendCodeMailDto;
import com.ecommerce.mailservice.dto.SendTokenMailDto;

public interface MailService {

    void sendLinkAccountVerificationMail(SendTokenMailDto sendMailDto);

    void sendPasswordResetEmail(String name, String to, String token);

    void sendCodeAccountVerificationMail(SendCodeMailDto sendMailDto);
}
