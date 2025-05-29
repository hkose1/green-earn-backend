package com.greenearn.challengeservice.client;

import com.greenearn.challengeservice.client.request.SendChallengeCompletedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailServiceClient {

    @PostMapping("/api/mail/send/challenge-completed")
    void sendChallengeCompletedMail(@RequestBody SendChallengeCompletedDto sendChallengeCompletedDto);
}
