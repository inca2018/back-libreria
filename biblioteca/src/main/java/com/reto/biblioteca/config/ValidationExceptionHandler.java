package com.reto.biblioteca.config;

import com.reto.biblioteca.exception.responses.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ValidationErrorResponse>handleValidationException(MethodArgumentNotValidException e)
    {
        ValidationErrorResponse response = new ValidationErrorResponse();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> response.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.unprocessableEntity().body(response);
    }
}
