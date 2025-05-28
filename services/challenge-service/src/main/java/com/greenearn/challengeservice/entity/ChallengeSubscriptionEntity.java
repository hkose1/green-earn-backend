package com.greenearn.challengeservice.entity;

import com.greenearn.challengeservice.enums.ChallengeSubscriptionProgressStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "challenge_subscriptions")
public class ChallengeSubscriptionEntity extends Auditable {

    @Column(nullable = false)
    private UUID challengeId;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private ChallengeSubscriptionProgressStatus challengeSubscriptionProgressStatus;
}
