package com.thd.base.util.validator;

import com.thd.base.util.anotation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String fieldName;
    private String dependFieldName;
    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        fieldName = constraintAnnotation.fieldName();
        dependFieldName = constraintAnnotation.dependFieldName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object==null) return true;
        try {
            Class<?> clazz = object.getClass();
            Field firstField = clazz.getDeclaredField(fieldName);
            Field secondField = clazz.getDeclaredField(dependFieldName);
            firstField.setAccessible(true);
            secondField.setAccessible(true);
            Object firstValue = firstField.get(object);
            Object secondValue = secondField.get(object);
            if(secondValue!=null && firstValue!=null && ObjectUtils.nullSafeEquals(firstValue, secondValue)){
                return true;
            }
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName).addConstraintViolation();
            constraintValidatorContext.disableDefaultConstraintViolation();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }
}
