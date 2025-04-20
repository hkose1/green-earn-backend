package com.ecommerce.authservice.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.security.SecureRandom;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "code_confirmations")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CodeConfirmationEntity  extends Auditable {

    @Column(name = "code")
    private String code;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class ,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user_id")
    private UserEntity userEntity;

    public CodeConfirmationEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.code = String.format("%06d", new SecureRandom().nextInt(1000000));
    }
}
