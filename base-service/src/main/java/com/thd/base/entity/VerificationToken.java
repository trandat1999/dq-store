package com.thd.base.entity;

import com.thd.base.enums.TokenEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_verification_token")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken extends BaseEntity{
    @Column(name = "token")
    private String token;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne()
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenEnum type;

    @Column(name = "password")
    private String password;
}
