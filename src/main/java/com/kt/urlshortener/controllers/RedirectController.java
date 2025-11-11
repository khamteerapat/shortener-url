package com.kt.urlshortener.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/r")
public class RedirectController {
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.google.com/?zx=1762824524891&no_sw_cr=1"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
