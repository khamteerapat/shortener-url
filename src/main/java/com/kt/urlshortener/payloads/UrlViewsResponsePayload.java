package com.kt.urlshortener.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UrlViewsResponsePayload {
    private UUID id;
    private String shortCode;
    private String originalLink;
    private Instant createdAt;
}
