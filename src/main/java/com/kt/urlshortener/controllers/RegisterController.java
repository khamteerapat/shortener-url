package com.kt.urlshortener.controllers;

import com.kt.urlshortener.payloads.RegisterRequestPayload;
import com.kt.urlshortener.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestPayload requestPayload){
        String result = authService.register(requestPayload.getUsername(),requestPayload.getPassword());
        return ResponseEntity.ok(result);
    }
}
