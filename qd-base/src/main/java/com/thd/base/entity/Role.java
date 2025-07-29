package com.thd.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Table(name = "tbl_role")
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseInformation implements GrantedAuthority {

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    private List<RolePermission> rolePermissions;

    @Override
    public String getAuthority() {
        return this.code;
    }
}
