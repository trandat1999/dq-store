package com.thd.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.base.dto.BaseResponse;
import com.thd.base.util.SystemMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.thd.base.util.ConstUtils.APPLICATION_JSON;
import static com.thd.base.util.SystemMessage.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {
    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (response.getStatus() == HttpStatus.OK.value()) {
            response.setContentType(APPLICATION_JSON);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            BaseResponse errorResponse = BaseResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(messageSource.getMessage(SystemMessage.UNAUTHORIZED, null, LocaleContextHolder.getLocale()))
                    .build();
            OutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        BaseResponse errorResponse = BaseResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(messageSource.getMessage(FORBIDDEN, null, LocaleContextHolder.getLocale()))
                .build();
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}
