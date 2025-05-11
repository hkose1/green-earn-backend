package com.greenearn.authservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenearn.authservice.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "roles")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RoleEntity extends Auditable {

    @Enumerated(EnumType.STRING)
    private Role role;
}
