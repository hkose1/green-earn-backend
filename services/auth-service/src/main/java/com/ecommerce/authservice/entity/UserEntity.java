package com.ecommerce.authservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity extends Auditable {

    @Column(unique = true)
    private String username;

    private String firstname;
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private RoleEntity role;
}
