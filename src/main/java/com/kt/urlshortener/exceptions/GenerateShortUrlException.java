package com.kt.urlshortener.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenerateShortUrlException extends RuntimeException{
    public GenerateShortUrlException(String message) {
        super(message);
    }

    public GenerateShortUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
