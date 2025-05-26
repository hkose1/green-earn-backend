package com.greenearn.challengeservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.challengeservice.enums.ChallengeDuration;
import jakarta.persistence.Entity;
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
    private ChallengeDuration challengeDuration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Float pointMultiplier;

}
