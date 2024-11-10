package com.youable.validation.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RestrictionValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Restriction {
    boolean required() default false;
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    PatternType patternType() default PatternType.NONE;
    String message() default "유효하지 않은 값입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    enum PatternType {
        NONE,
        PHONE,
        EMAIL
    }
}
