package com.greenearn.challengeservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import com.greenearn.challengeservice.enums.ChallengeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "challenges")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChallengeEntity extends Auditable {
    private String title;
    private String description;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private ChallengeDuration challengeDuration;
    @Enumerated(EnumType.STRING)
    private ChallengeStatus challengeStatus;
    private Integer returnedPoints;
    @OneToOne(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ChallengeConditionEntity challengeCondition;
}
