package com.thd.user.rest;

import com.thd.base.dto.BaseResponse;
import com.thd.base.repository.UserRepository;
import com.thd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/current")
    public BaseResponse currentUser(){
        return userService.getCurrentUser();
    }
}
