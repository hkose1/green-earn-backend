package com.greenearn.customerservice.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerEntity extends Auditable {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerPointEntity customerPoint;
}
