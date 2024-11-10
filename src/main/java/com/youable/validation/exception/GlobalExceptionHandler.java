package com.youable.validation.exception;

import com.youable.validation.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@Slf4j
@RestControllerAdvice
@Validated
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleReadableException(
            HttpMessageNotReadableException ex
    ) {
        ErrorResponse response = ErrorResponse.of(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResponse response = ErrorResponse.of(ex.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        ErrorResponse response = ErrorResponse.of(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse response = ErrorResponse.of(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
