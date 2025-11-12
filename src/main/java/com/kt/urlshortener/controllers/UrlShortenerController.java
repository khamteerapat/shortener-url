package com.kt.urlshortener.controllers;

import com.kt.urlshortener.payloads.UrlShortenerRequestPayload;
import com.kt.urlshortener.payloads.UrlShortenerResponsePayload;
import com.kt.urlshortener.services.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shorten")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<UrlShortenerResponsePayload> shortenUrl(@RequestBody @Valid UrlShortenerRequestPayload requestPayload) throws BadRequestException {
        if(!UrlValidator.getInstance().isValid(requestPayload.getOriginalUrl())){
            throw new BadRequestException("url format invalid.");
        }
        String shortUrl = urlShortenerService.generateShortUrl(requestPayload.getOriginalUrl());
        return ResponseEntity.ok(new UrlShortenerResponsePayload(shortUrl));
    }
}
