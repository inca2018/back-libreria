package com.reto.biblioteca.exception.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse {

    private final Map<String, List<String>> errors = new HashMap<>();

    public void addError(String field, String error) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(error);
    }
}
