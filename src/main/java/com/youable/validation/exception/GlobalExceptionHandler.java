package com.youable.validation.exception;

import com.youable.validation.common.enums.ResultCode;
import com.youable.validation.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        String errorMessage = ex.getMessage(); // JSON parse error: Cannot deserialize value of type `int` from String "한개": not a valid `int` value
        String uri = request.getRequestURI(); // /api/v1/book

        ErrorResponse response = ErrorResponse.builder()
                .code(ResultCode.NOT_VALID_DATA.getCode())
                .msg(errorMessage)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);
        String field = fieldError.getField(); // amount
        String errorMessage = fieldError.getDefaultMessage(); // 최대로 등록할 수 있는 수량은 100개 입니다.
        Object value = fieldError.getRejectedValue(); // 10000
        String objectName = fieldError.getObjectName(); //registBookRequest

        ErrorResponse response = ErrorResponse.builder()
                .code(ResultCode.NOT_VALID_DATA.getCode())
                .msg(errorMessage + " " + field + "필드의 값 " + value + "를 확인하세요")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        ParameterValidationResult results = ex.getAllValidationResults().get(0);

        ErrorResponse response = ErrorResponse.builder()
                .code(ResultCode.NOT_VALID_DATA.getCode())
                .msg(results.getResolvableErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.status(ex.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        ConstraintViolation<?> constraintViolations = ex.getConstraintViolations().iterator().next();

        String errorMessage = constraintViolations.getMessage();
        ErrorResponse response = ErrorResponse.builder()
                .code(ResultCode.NOT_VALID_DATA.getCode())
                .msg(errorMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
