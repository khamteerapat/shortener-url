package com.kt.urlshortener.services.impl;

import com.kt.urlshortener.repositories.LinksMappingRepository;
import com.kt.urlshortener.services.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {
    private final LinksMappingRepository linksMappingRepository;

    @Override
    @Cacheable(value = "redirect_cache",key = "#shortCode")
    public String getOriginalUrlForRedirect(String shortCode) {
        return linksMappingRepository.getOriginalUrlByShortCode(shortCode);
    }
}
