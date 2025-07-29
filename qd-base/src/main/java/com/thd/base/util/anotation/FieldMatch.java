package com.thd.base.util.anotation;

import com.thd.base.util.validator.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FieldMatchValidator.class})
public @interface FieldMatch {
    String fieldName();
    String dependFieldName();
    String message() default "{validation.mismatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
