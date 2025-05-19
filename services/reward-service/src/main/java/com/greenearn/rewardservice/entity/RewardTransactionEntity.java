package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import com.greenearn.rewardservice.enums.RewardCategory;
import com.greenearn.rewardservice.enums.RewardTransactionStatus;
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
@Entity(name = "reward_transactions")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardTransactionEntity extends Auditable {
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private RewardCategory rewardCategory;
    @Enumerated(EnumType.STRING)
    private RewardTransactionStatus rewardTransactionStatus;
    @Enumerated(EnumType.STRING)
    private BrandType brandType;
    private Integer quantity;
    private Integer totalCostPoint;
    private String failureMessage;

}
