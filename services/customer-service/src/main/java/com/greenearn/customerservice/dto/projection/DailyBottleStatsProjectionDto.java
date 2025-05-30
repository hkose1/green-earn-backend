package com.greenearn.customerservice.dto.projection;

import java.time.LocalDate;

public interface DailyBottleStatsProjectionDto {
    LocalDate getDate();
    Integer getTotalSmallBottles();
    Integer getTotalMediumBottles();
    Integer getTotalLargeBottles();
    Integer getTotalPoints();
}
