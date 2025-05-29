package com.greenearn.customerservice.dto.projection;

import java.util.UUID;

public interface TopCustomerDto {
    UUID getCustomerId();
    Integer getTotalPoints();
}
