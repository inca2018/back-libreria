package com.reto.biblioteca.exception;


import com.reto.biblioteca.exception.responses.ValidationErrorResponse;

public class BaseException extends RuntimeException {

    private final ValidationErrorResponse errors;

    public BaseException(ValidationErrorResponse errors) {
        this.errors = errors;
    }

    public ValidationErrorResponse getErrors() {
        return errors;
    }
}

