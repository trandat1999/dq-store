package com.thd.base.service;

import com.thd.base.entity.User;
import com.thd.base.exception.TempLockedException;
import com.thd.base.repository.UserRepository;
import com.thd.base.util.ConstUtils;
import com.thd.base.util.DateUtils;
import com.thd.base.util.SystemMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
    private final UserRepository userRepository;
    @Value("${application.security.timeLockedTemp}")
    private Integer timeLockedTemp;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = ((User) event.getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        userRepository.updateLastLoginUser(new Date(),user.getId());
    }

    @Override
    public User loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (user.getLockedDate() != null && DateUtils.plusMinute(user.getLockedDate(), timeLockedTemp).after(new Date())) {
            throw new TempLockedException(getMessage(SystemMessage.USER_IS_LOCKED_TEMP,
                    DateUtils.formatDate(DateUtils.plusMinute(user.getLockedDate(), timeLockedTemp), ConstUtils.DATE_FORMAT_DMYHMS)));
        }
        return user;
    }
}
