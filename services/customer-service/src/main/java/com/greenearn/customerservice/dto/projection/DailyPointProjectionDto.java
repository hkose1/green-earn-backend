package com.greenearn.customerservice.dto.projection;

import java.time.LocalDate;

public interface DailyPointProjectionDto {
    LocalDate getDate();
    Integer getTotalPoints();
}
