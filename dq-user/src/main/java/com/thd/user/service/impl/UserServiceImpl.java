package com.thd.user.service.impl;

import com.thd.base.dto.BaseResponse;
import com.thd.base.entity.User;
import com.thd.base.repository.UserRepository;
import com.thd.base.service.BaseService;
import com.thd.base.util.AuthenticationUtil;
import com.thd.base.util.SystemMessage;
import com.thd.user.dto.UserDto;
import com.thd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationUtil authenticationUtil;
    @Override
    public BaseResponse getCurrentUser() {
        User user = authenticationUtil.getCurrentUser();
        if(user==null){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND));
        }
        User currentUser = userRepository.findById(user.getId()).orElse(null);
        return getResponse200(new UserDto(currentUser), getMessage(SystemMessage.SUCCESS));
    }
}
