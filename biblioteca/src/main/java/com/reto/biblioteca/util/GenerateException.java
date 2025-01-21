package com.reto.biblioteca.util;


import com.reto.biblioteca.exception.BaseException;
import com.reto.biblioteca.exception.responses.ValidationErrorResponse;

public class GenerateException {
    private static ValidationErrorResponse GenerarObjetoError(String value, String message){

        ValidationErrorResponse response = new ValidationErrorResponse();
        response.addError(value,message);

        return response;
    }

    public static void lanzarExcepcion(String value, String message) {
        ValidationErrorResponse errorResponse = GenerarObjetoError(value, message);
        throw new BaseException(errorResponse);
    }
}
