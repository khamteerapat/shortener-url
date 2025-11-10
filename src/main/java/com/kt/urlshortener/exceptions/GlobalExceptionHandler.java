package com.kt.urlshortener.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenerateShortUrlException.class)
    public ResponseEntity<String> handleGenerateShortUrlException(GenerateShortUrlException ex) {
        log.error("GenerateShortUrlException: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body("Generate Short URL error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body("Unexpected error occurred.");
    }
}
