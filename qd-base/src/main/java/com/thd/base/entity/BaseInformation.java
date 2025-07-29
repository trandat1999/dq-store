package com.thd.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseInformation extends BaseEntity{
    @Column(name = "name")
    protected String name;
    @Column(name = "description",columnDefinition = "text")
    protected String description;
    @Column(name = "short_description")
    protected String shortDescription;
    @Column(name = "code",unique = true)
    protected String code;
}
