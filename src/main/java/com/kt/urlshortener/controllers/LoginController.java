package com.kt.urlshortener.controllers;

import com.kt.urlshortener.payloads.RegisterRequestPayload;
import com.kt.urlshortener.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid RegisterRequestPayload requestPayload){
        String result = authService.login(requestPayload.getUsername(),requestPayload.getPassword());
        return ResponseEntity.ok(result);
    }
}
