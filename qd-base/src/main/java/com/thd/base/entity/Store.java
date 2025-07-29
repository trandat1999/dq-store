package com.thd.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "tbl_store")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Store extends BaseInformation{
}
