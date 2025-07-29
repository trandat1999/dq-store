package com.thd.user.service.impl;

import com.thd.base.dto.BaseResponse;
import com.thd.base.entity.AccessToken;
import com.thd.base.entity.RefreshToken;
import com.thd.base.enums.TokenEnum;
import com.thd.base.repository.*;
import com.thd.base.service.JwtService;
import com.thd.base.util.SystemMessage;
import com.thd.user.dto.request.LoginRequest;
import com.thd.user.dto.request.RegisterRequest;
import com.thd.user.dto.response.AuthResponse;
import com.thd.user.service.AuthService;
import com.thd.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author DatNuclear 6/8/2025 10:42 PM
 * @project user-service
 * @package com.thd.user.service.impl
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {
    @Value("${application.security.jwt.expiration.refreshToken}")
    private long refreshTokenExpiration;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VerificationTokenRepository verificationTokenRepository;
    @Override
    public BaseResponse login(LoginRequest request) {
        HashMap<String, String> validations = validation(request);
        if (!CollectionUtils.isEmpty(validations)) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtService.generateToken(request.getUsername());
        var refreshToken = UUID.randomUUID().toString();
        revokeAllTokenAndRefreshToken(user.getUsername(),true);
        saveToken(user.getUsername(), token);
        saveRefreshToken(user.getUsername(), refreshToken);
        AuthResponse authResponse = AuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(token).build();
        return getResponse200(authResponse, getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public BaseResponse verifyAccount(String token) {
        return null;
    }

    @Override
    public BaseResponse generateActiveToken(String username) {
        return null;
    }

    @Override
    public BaseResponse activeNewPassword(String token) {
        return null;
    }

    @Override
    public BaseResponse generateNewToken(String token) {
        return null;
    }

    @Override
    public BaseResponse refreshToken(String token) {
        return null;
    }

    private void saveToken(String username, String jwtToken) {
        var entity = accessTokenRepository.findByToken(jwtToken).orElse(null);
        if (entity != null) {
            return;
        }
        var token = AccessToken.builder()
                .username(username)
                .token(jwtToken)
                .type(TokenEnum.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        accessTokenRepository.save(token);
    }

    private void saveRefreshToken(String username, String jwtToken) {
        var token = RefreshToken.builder()
                .username(username)
                .token(jwtToken)
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .revoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    private void revokeAllTokenAndRefreshToken(String username, boolean revokeRefreshToken) {
        List<AccessToken> tokens = accessTokenRepository.findAllByUsername(username);
        if (!CollectionUtils.isEmpty(tokens)) {
            tokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            accessTokenRepository.saveAll(tokens);
        }
        if(revokeRefreshToken){
            List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUsername(username);
            if (!CollectionUtils.isEmpty(refreshTokens)) {
                refreshTokens.forEach(token -> {
                    token.setRevoked(true);
                });
                refreshTokenRepository.saveAll(refreshTokens);
            }
        }
    }
}
