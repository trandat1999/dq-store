package com.thd.base.service;

import com.thd.base.entity.Role;
import com.thd.base.entity.RolePermission;
import com.thd.base.enums.LogicalEnum;
import com.thd.base.util.anotation.CheckPermission;
import com.thd.base.util.anotation.CheckPermissions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PermissionService {
    public boolean hasPermission(Authentication auth, String module, String action) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (!(authority instanceof Role role)) continue;
            if (!CollectionUtils.isEmpty(role.getRolePermissions())) {
                for (RolePermission rp : role.getRolePermissions()) {
                    if (rp.getModule().getCode().equals(module) && rp.getAction().getCode().equals(action)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
