package com.thd.base.entity;

import com.thd.base.enums.TokenEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_token")
public class AccessToken extends BaseEntity{
    @Column(name="token",unique = true)
    private String token;
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private TokenEnum type = TokenEnum.BEARER;
    @Column(name="revoked")
    private boolean revoked;
    @Column(name="expired")
    private boolean expired;
    @Column(name="username")
    private String username;
}
