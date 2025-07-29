package com.thd.base.util.anotation;

import com.thd.base.enums.LogicalEnum;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermissions {
    CheckPermission[] value();
    LogicalEnum logical() default LogicalEnum.AND;
}
