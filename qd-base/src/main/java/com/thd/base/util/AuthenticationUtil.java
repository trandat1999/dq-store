package com.thd.base.util;

import com.thd.base.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationUtil {

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }catch (Exception e){
            }
        }
        return null;
    }

}
