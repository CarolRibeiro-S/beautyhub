package com.beautyhub.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException e) {
        log.error("Erro capturado: {} | Causa: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "null");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Erro capturado: {} | Causa: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "null", e);

        if (e.getMessage() != null && e.getMessage().contains("Horário já ocupado")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.internalServerError()
                .body("Erro: " + e.getMessage());
    }
}