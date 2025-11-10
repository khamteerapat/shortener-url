package com.kt.urlshortener.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlShortenerResponsePayload {
    @JsonProperty("short_url")
    private String shortUrl;
}
