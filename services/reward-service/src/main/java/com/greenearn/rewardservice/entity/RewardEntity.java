package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.RewardCategory;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rewards")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardEntity extends Auditable {
    private String title;
    private String imageUrl;
    private RewardCategory rewardCategory;
    private RewardProviderEntity rewardProviderEntity;
    private String description;
    private Integer costPoint;
    private Integer stock;
}
