package com.thd.user.rest;

import com.thd.base.dto.BaseResponse;
import com.thd.user.dto.request.LoginRequest;
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
public class AuthController {
    public BaseResponse login(@RequestBody LoginRequest request){
        return null;
    }
}
