package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.RewardCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rewards")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardEntity extends Auditable {
    private String title;
    private String description;
    private String imageUrl;
    private Integer costPoints;
    private Boolean isActive;
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private RewardCategory rewardCategory;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reward_provider_id", nullable = false)
    private RewardProviderEntity rewardProvider;
}
