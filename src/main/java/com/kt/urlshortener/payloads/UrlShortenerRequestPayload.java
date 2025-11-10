package com.kt.urlshortener.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlShortenerRequestPayload {
    @JsonProperty("original_url")
    private String originalUrl;
}
