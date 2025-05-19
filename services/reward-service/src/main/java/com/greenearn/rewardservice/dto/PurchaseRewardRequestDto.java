package com.greenearn.rewardservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class PurchaseRewardRequestDto {
    @NotNull(message = "reward id can not bu null")
    private UUID rewardId;
    @NotNull(message = "Quantity can not be null or empty")
    private Integer quantity;
}
