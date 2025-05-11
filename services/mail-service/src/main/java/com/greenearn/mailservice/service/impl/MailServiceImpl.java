package com.greenearn.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.greenearn.mailservice.dto.SendCodeMailDto;
import com.greenearn.mailservice.dto.SendTokenMailDto;
import com.greenearn.mailservice.service.MailService;
import com.greenearn.mailservice.util.MailUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Reset Password Request";
    private final JavaMailSender mailSender;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    @Async
    public void sendLinkAccountVerificationMail(SendTokenMailDto sendMailDto) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(sendMailDto.getTo());
            message.setText(MailUtils.getLinkAccountVerificationEmailMessage(sendMailDto.getName(), host, sendMailDto.getToken()));
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void sendCodeAccountVerificationMail(SendCodeMailDto sendMailDto) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(sendMailDto.getTo());
            message.setText(MailUtils.getCodeAccountVerificationEmailMessage(sendMailDto.getName(), sendMailDto.getCode()));
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void sendPasswordResetEmail(String name, String to, String token) {

    }


}
