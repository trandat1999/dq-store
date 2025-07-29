package com.thd.user.dto.request;

import com.thd.base.util.SystemMessage;
import com.thd.base.util.anotation.FieldMatch;
import com.thd.user.util.Constaint;
import com.thd.user.util.SystemVariable;
import jakarta.validation.constraints.*;
import lombok.Data;

import static com.thd.user.util.UserSystemMessage.VALIDATION_FIELD_MATCH;
import static com.thd.user.util.UserSystemMessage.VALIDATION_USERNAME_PATTERN;

@Data
@FieldMatch(message = VALIDATION_FIELD_MATCH,
        fieldName = SystemVariable.CONFIRM_PASSWORD,
        dependFieldName = SystemVariable.PASSWORD)
public class RegisterRequest {
    @Pattern(regexp = Constaint.PATTERN_USERNAME, message = VALIDATION_USERNAME_PATTERN)
    @NotNull(message = SystemMessage.VALIDATION_NOTNULL)
    @NotBlank(message = SystemMessage.VALIDATION_NOT_BLANK)
    private String username;
    @NotNull(message = SystemMessage.VALIDATION_NOTNULL)
    @Size(min = Constaint.MIN_LENGTH_PASSWORD_REQUIRED, message = SystemMessage.VALIDATION_MIN_LENGTH)
    private String password;
    @NotNull(message = SystemMessage.VALIDATION_NOTNULL)
    @Size(min = Constaint.MIN_LENGTH_PASSWORD_REQUIRED, message = SystemMessage.VALIDATION_MIN_LENGTH)
    private String confirmPassword;
    @Email(message = SystemMessage.VALIDATION_EMAIL)
    @NotNull(message = SystemMessage.VALIDATION_NOTNULL)
    @NotBlank(message = SystemMessage.VALIDATION_NOT_BLANK)
    private String email;
    @NotNull(message = SystemMessage.VALIDATION_NOTNULL)
    @NotBlank(message = SystemMessage.VALIDATION_NOT_BLANK)
    private String fullName;
}
