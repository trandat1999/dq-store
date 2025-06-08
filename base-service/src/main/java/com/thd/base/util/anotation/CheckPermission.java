package com.thd.base.util.anotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Repeatable(CheckPermissions.class)
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@permissionService.hasPermission(authentication, #module, #action)")
public @interface CheckPermission {
    String module();
    String action();
}
