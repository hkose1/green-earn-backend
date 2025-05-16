package com.greenearn.rewardservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.rewardservice.enums.BrandType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reward_providers")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RewardProviderEntity extends Auditable {
    @Enumerated(EnumType.STRING)
    private BrandType brandType;
    
    private String name;
    private String description;
    private String logoUrl;
    
    @OneToMany(mappedBy = "rewardProvider", cascade = CascadeType.ALL)
    private List<RewardEntity> rewards;
}
