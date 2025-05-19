package com.greenearn.customerservice.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UpdateCustomerPointsResponseDto {
    private Integer totalCostPoints;
}
