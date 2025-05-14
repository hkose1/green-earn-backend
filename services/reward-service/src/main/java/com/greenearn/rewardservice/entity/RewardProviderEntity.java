package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reward_providers")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardProviderEntity extends Auditable {
    private BrandType brandType;
    private String description;
    private Boolean isActive;
}
