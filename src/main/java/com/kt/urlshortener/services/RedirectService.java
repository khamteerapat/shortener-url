package com.kt.urlshortener.services;

public interface RedirectService {
    String getOriginalUrlForRedirect(String shortCode);
}
