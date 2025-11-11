package com.kt.urlshortener.exceptions;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String NOT_FOUND_MESSAGE = "find not found.";

    @ExceptionHandler(GenerateShortUrlException.class)
    public ResponseEntity<String> handleGenerateShortUrlException(GenerateShortUrlException ex) {
        log.error("GenerateShortUrlException: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body("Generate Short URL error: " + ex.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<String> handleNotFoundException(NoResultException ex){
        log.error("Exception no result : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(NOT_FOUND_MESSAGE);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
        log.error("Exception bad request : {}",ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("bad request.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body("Unexpected error occurred.");
    }
}
