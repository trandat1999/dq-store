package com.thd.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_action")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Action extends BaseInformation{
}
