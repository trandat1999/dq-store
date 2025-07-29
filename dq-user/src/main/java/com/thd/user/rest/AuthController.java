package com.thd.user.rest;

import com.thd.base.dto.BaseResponse;
import com.thd.base.util.anotation.LogActivity;
import com.thd.user.dto.request.LoginRequest;
import com.thd.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DatNuclear 6/8/2025 10:36 PM
 * @project user-service
 * @package com.thd.user.rest
 */
@RestController
@RequestMapping(name = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @LogActivity
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
