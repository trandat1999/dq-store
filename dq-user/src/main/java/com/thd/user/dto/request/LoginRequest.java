package com.thd.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.thd.base.util.SystemMessage.VALIDATION_NOTNULL;
import static com.thd.base.util.SystemMessage.VALIDATION_NOT_BLANK;

@Data
public class LoginRequest {
    @NotNull(message = VALIDATION_NOTNULL)
    @NotBlank(message = VALIDATION_NOT_BLANK)
    private String username;
    @NotNull(message = VALIDATION_NOTNULL)
    @NotBlank(message = VALIDATION_NOT_BLANK)
    private String password;
}
