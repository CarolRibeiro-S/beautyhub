package com.beautyhub.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Erro capturado: {} | Causa: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "null", e);
        return ResponseEntity.internalServerError()
                .body("Erro: " + e.getMessage() + " | Tipo: " + e.getClass().getName() + " | Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "null"));
    }
}
