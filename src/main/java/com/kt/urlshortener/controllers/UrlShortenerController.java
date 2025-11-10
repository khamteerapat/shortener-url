package com.kt.urlshortener.controllers;

import com.kt.urlshortener.payloads.UrlShortenerRequestPayload;
import com.kt.urlshortener.payloads.UrlShortenerResponsePayload;
import com.kt.urlshortener.services.UrlShortenerService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UrlShortenerResponsePayload> shortenUrl(@RequestBody UrlShortenerRequestPayload requestPayload){
        String shortUrl = urlShortenerService.generateShortUrl(requestPayload.getOriginalUrl());
        return ResponseEntity.ok(new UrlShortenerResponsePayload(shortUrl));
    }
}
