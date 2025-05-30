package com.greenearn.customerservice.dto.projection;

public interface MonthlyStatsDto {
    Integer getTotalCustomers();
    Integer getNewCustomers();

    Integer getMonthlySmallBottles();
    Integer getMonthlyMediumBottles();
    Integer getMonthlyLargeBottles();
    Integer getMonthlyPoints();

    Integer getTotalSmallBottles();
    Integer getTotalMediumBottles();
    Integer getTotalLargeBottles();
    Integer getTotalPoints();
}
