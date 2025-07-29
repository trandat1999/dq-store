package com.thd.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_module")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Module extends BaseInformation {
}
