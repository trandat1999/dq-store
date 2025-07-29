package com.thd.base.util.component;

import com.thd.base.enums.LogicalEnum;
import com.thd.base.service.PermissionService;
import com.thd.base.util.anotation.CheckPermission;
import com.thd.base.util.anotation.CheckPermissions;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckPermissionsAspect {
    private final PermissionService permissionService;

    @Before("@annotation(com.thd.base.util.anotation.CheckPermissions) || @within(com.thd.base.util.anotation.CheckPermissions)")
    public void checkPermissions(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Method method = getMethodFromJoinPoint(joinPoint);
        CheckPermissions checkPermissions = getCheckPermissionsAnnotation(joinPoint, method);
        if (checkPermissions == null) return; // không có annotation thì cho qua
        CheckPermission[] permissions = checkPermissions.value();
        LogicalEnum logical = checkPermissions.logical();

        boolean result;
        if (logical == LogicalEnum.AND) {
            result = Arrays.stream(permissions)
                    .allMatch(p -> permissionService.hasPermission(auth, p.module(), p.action()));
        } else {
            result = Arrays.stream(permissions)
                    .anyMatch(p -> permissionService.hasPermission(auth, p.module(), p.action()));
        }

        if (!result) {
            throw new AccessDeniedException("AccessDenied");
        }
    }

    private Method getMethodFromJoinPoint(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private CheckPermissions getCheckPermissionsAnnotation(JoinPoint joinPoint, Method method) {
        if (method.isAnnotationPresent(CheckPermissions.class)) {
            return method.getAnnotation(CheckPermissions.class);
        }
        Class<?> targetClass = joinPoint.getTarget().getClass();
        if (targetClass.isAnnotationPresent(CheckPermissions.class)) {
            return targetClass.getAnnotation(CheckPermissions.class);
        }
        return null;
    }
}
