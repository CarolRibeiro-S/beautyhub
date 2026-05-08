package com.beautyhub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("Erro: " + e.getMessage() + " | Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "null"));
    }
}