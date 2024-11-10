package com.youable.validation.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class RestrictionValidator implements ConstraintValidator<Restriction, Object> {

    private boolean required;
    private int min;
    private int max;
    private Restriction.PatternType patternType;
    private static final Pattern PHONE_PATTERN = Pattern.compile("^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");

    @Override
    public void initialize(Restriction constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.patternType = constraintAnnotation.patternType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (required && value == null) {
            return false;
        }

        if (value instanceof String stringValue) {
            if (required && stringValue.trim().isEmpty()) {
                return false;
            }

            if (!required && stringValue.trim().isEmpty()) {
                return true;
            }

            if (stringValue.length() < min || stringValue.length() > max) {
                return false;
            }

            if (patternType == Restriction.PatternType.PHONE && !PHONE_PATTERN.matcher(stringValue).matches()) {
                return false;
            }

            if (patternType == Restriction.PatternType.EMAIL && !EMAIL_PATTERN.matcher(stringValue).matches()) {
                return false;
            }

        } else if (value instanceof Integer intValue) {
            if (intValue < min || intValue > max) {
                return false;
            }
        }

        return true;
    }
}
