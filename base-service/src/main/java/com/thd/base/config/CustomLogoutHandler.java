package com.thd.base.config;

import com.thd.base.repository.AccessTokenRepository;
import com.thd.base.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.thd.base.util.ConstUtils.AUTHORIZATION;
import static com.thd.base.util.ConstUtils.BEARER;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null ||!authHeader.startsWith(BEARER)) {
            return;
        }
        final String jwt = authHeader.substring(BEARER.length());

        var storedToken = accessTokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            var refreshTokens = refreshTokenRepository.findAllByUsername(storedToken.getUsername());
            if(!CollectionUtils.isEmpty(refreshTokens)){
                refreshTokens.forEach(refreshToken -> refreshToken.setRevoked(true));
                refreshTokenRepository.saveAll(refreshTokens);
            }
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            accessTokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
