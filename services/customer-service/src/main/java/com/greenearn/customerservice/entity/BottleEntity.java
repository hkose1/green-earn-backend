package com.greenearn.customerservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.customerservice.enums.BottleSizeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bottles")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BottleEntity extends Auditable {
    private Integer points;
    @Enumerated(EnumType.STRING)
    private BottleSizeType bottleSize;
}
