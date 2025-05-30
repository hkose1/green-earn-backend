package com.greenearn.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyBottleStats {
    private final Integer totalSmallBottles;
    private final Integer totalMediumBottles;
    private final Integer totalLargeBottles;
    private final Integer totalPoints;
}
