package com.greenearn.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer_points")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerPointEntity extends Auditable {
    
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;
    
    private Integer totalNumberOfSmallBottles;
    private Integer totalNumberOfMediumBottles;
    private Integer totalNumberOfLargeBottles;
    private Integer totalPoints;
}
