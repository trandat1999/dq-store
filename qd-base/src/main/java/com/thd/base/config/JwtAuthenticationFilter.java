package com.thd.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.base.dto.BaseResponse;
import com.thd.base.entity.User;
import com.thd.base.repository.AccessTokenRepository;
import com.thd.base.service.JwtService;
import com.thd.base.service.UserDetailsServiceImpl;
import com.thd.base.util.SystemMessage;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

import static com.thd.base.util.ConstUtils.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtProvider;
    private final AccessTokenRepository accessTokenRepository;
    private final MessageSource messageSource;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt)) {
            try {
                String username = jwtProvider.getUsernameFromToken(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = this.userDetailsService.loadUserByUsername(username);
                    var isTokenValid = accessTokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
                    if (jwtProvider.isValidToken(jwt, user.getUsername()) && isTokenValid) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                ObjectMapper mapper = new ObjectMapper();
                BaseResponse errorDetail = BaseResponse.builder()
                        .build();
                HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
                log.error(e.getMessage());
                if (e instanceof ExpiredJwtException) {
                    errorDetail.setMessage(messageSource.getMessage(SystemMessage.JWT_IS_EXPIRED, null, LocaleContextHolder.getLocale()));
                } else {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    errorDetail.setMessage(messageSource.getMessage(SystemMessage.JWT_IS_INVALID, null, LocaleContextHolder.getLocale()));
                }
                errorDetail.setStatus(httpStatus.value());
                response.setStatus(httpStatus.value());
                response.setContentType(APPLICATION_JSON);
                response.setCharacterEncoding(UTF8);
                OutputStream outputStream = response.getOutputStream();
                mapper.writeValue(outputStream, errorDetail);
                outputStream.flush();
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest arg0) {
        String token = arg0.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        }
        return null;
    }
}
