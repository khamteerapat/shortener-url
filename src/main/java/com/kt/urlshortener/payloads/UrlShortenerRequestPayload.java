package com.kt.urlshortener.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlShortenerRequestPayload {
    @JsonProperty("original_url")
    @NotEmpty(message = "url can't be null")
    private String originalUrl;
}
