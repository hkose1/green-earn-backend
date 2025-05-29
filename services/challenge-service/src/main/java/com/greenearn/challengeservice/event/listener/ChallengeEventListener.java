package com.greenearn.challengeservice.event.listener;


import com.greenearn.challengeservice.client.MailServiceClient;
import com.greenearn.challengeservice.client.request.SendChallengeCompletedDto;
import com.greenearn.challengeservice.event.ChallengeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeEventListener {

    private final MailServiceClient mailServiceClient;

    @EventListener
    public void onChallengeEvent(ChallengeEvent event) {
        switch (event.getEventType()) {
            case COMPLETED -> {
                SendChallengeCompletedDto challengeCompletedDto = SendChallengeCompletedDto.builder()
                        .to(event.getUserDto().getEmail())
                        .name(event.getUserDto().getFirstname() + " " + event.getUserDto().getLastname())
                        .challengeTitle(event.getChallengeEntity().getTitle())
                        .challengeDescription(event.getChallengeEntity().getDescription())
                        .earnedPointsFromChallenge(event.getChallengeEntity().getReturnedPoints())
                        .build();
                mailServiceClient.sendChallengeCompletedMail(challengeCompletedDto);
            }
        }
    }
}
