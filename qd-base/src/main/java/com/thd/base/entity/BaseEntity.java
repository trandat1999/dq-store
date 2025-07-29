package com.thd.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity extends AuditableEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @UuidGenerator
    @GeneratedValue
    protected UUID id;

    @Column(name = "voided")
    protected Boolean voided;
}
