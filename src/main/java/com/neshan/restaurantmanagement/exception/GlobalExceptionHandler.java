package com.neshan.restaurantmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNoSuchElementFoundException(NoSuchElementFoundException exc) {

        ErrorResponse err = buildErrorResponse(exc);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception exc) {

        ErrorResponse err = buildErrorResponse(exc);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exc) {

        ErrorResponse err = buildErrorResponse(exc);

        for (FieldError fieldError : exc.getBindingResult().getFieldErrors()) {
            err.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ErrorResponse buildErrorResponse(Exception exc) {

        return ErrorResponse
                .builder()
                .message(exc.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
