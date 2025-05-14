package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import com.greenearn.rewardservice.enums.RewardCategory;
import com.greenearn.rewardservice.enums.RewardTransactionStatus;
import jakarta.persistence.Entity;
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
    private UUID customerId;
    private RewardCategory rewardCategory;
    private RewardTransactionStatus rewardTransactionStatus;
    private BrandType brandType;
    private Integer quantity;
    private Integer totalCostPoint;

}
