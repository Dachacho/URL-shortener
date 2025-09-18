package com.example.url_shortener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalErrorHandler {
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body("Bad Request: " + ex.getMessage());
    }

    public ResponseEntity<String> handleEverythingElse(RuntimeException ex){
        return ResponseEntity.internalServerError().body("Internal Server Error: " + ex.getMessage());
    }
}
