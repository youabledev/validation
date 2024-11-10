package com.youable.validation.dto.response;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.youable.validation.common.enums.ResultCode;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {
    private final int code;
    private final String msg;
    private final List<FieldError> fieldErrors;

    private ErrorResponse(ResultCode resultCode, List<FieldError> fieldErrors) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(
                    ResultCode.NOT_VALID_DATA,
                    FieldError.of(bindingResult)
            );
    }

    public static ErrorResponse of(HttpMessageNotReadableException ex) {
        return new ErrorResponse(
                ResultCode.NOT_VALID_TYPE,
                FieldError.of(ex)
        );
    }

    public static ErrorResponse of(HandlerMethodValidationException ex) {
        return new ErrorResponse(
                ResultCode.NOT_VALID_DATA,
                FieldError.of(ex)
        );
    }

    public static ErrorResponse of(ConstraintViolationException ex) {
        return new ErrorResponse(
                ResultCode.NOT_VALID_DATA,
                FieldError.of(ex)
        );
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final Object rejectedValue;
        private final String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(HttpMessageNotReadableException ex) {
            if (ex.getCause() instanceof JsonMappingException jsonMappingException) {
                return jsonMappingException.getPath().stream()
                        .map(reference -> new FieldError(
                                reference.getFieldName(),
                                null,
                                ex.getMessage()
                        ))
                        .collect(Collectors.toList());
            }
            return null;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        public static List<FieldError> of(HandlerMethodValidationException ex) {
            return ex.getAllValidationResults().stream()
                    .map(result -> new FieldError(
                            result.getMethodParameter().getParameterName(),
                            result.getArgument(),
                            result.getResolvableErrors().get(0).getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
        }

        public static List<FieldError> of(ConstraintViolationException ex) {
            return ex.getConstraintViolations().stream()
                    .map(violation -> new FieldError(
                            violation.getPropertyPath().toString(),
                            violation.getInvalidValue(),
                            violation.getMessage()
                    ))
                    .collect(Collectors.toList());
        }

    }
}
