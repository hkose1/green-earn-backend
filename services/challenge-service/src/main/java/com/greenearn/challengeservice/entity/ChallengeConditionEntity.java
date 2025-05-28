package com.greenearn.challengeservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "challenge_conditions")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChallengeConditionEntity extends Auditable {

    @OneToOne
    @JoinColumn(name = "challenge_id", nullable = false, unique = true)
    private ChallengeEntity challenge;

    private Integer requiredNumberOfSmallBottles;
    private Integer requiredNumberOfMediumBottles;
    private Integer requiredNumberOfLargeBottles;

}
