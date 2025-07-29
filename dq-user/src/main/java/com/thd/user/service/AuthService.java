package com.thd.user.service;

import com.thd.base.dto.BaseResponse;
import com.thd.user.dto.request.LoginRequest;
import com.thd.user.dto.request.RegisterRequest;

/**
 * @author DatNuclear 6/8/2025 10:41 PM
 * @project user-service
 * @package com.thd.user.service
 */
public interface AuthService {
    BaseResponse login(LoginRequest request);
    BaseResponse register(RegisterRequest request);
    BaseResponse verifyAccount(String token);
    BaseResponse generateActiveToken(String username);
    BaseResponse activeNewPassword(String token);
    BaseResponse generateNewToken(String token);
    BaseResponse refreshToken(String token);
}
