package com.reto.biblioteca.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Util {
    
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public static boolean isInvalidRequest(Object obj) {
        return obj == null;
    }

    public static void logRequest(String endpoint, String payload) {
        logger.info("Request to {}: {}", endpoint, payload);
    }

    public static void logResponse(ResponseEntity<?> response) {
        logger.info("Response status: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody());
    }

    public static ResponseEntity<?> logAndRespond(HttpStatus status, Object body) {
        ResponseEntity<?> response = ResponseEntity.status(status).body(body);
        logResponse(response);
        return response;
    }

}
