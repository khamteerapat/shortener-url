package com.kt.urlshortener.services.impl;

import com.kt.urlshortener.repositorys.LinksMappingRepository;
import com.kt.urlshortener.services.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {
    private final LinksMappingRepository linksMappingRepository;

    @Override
    public String getOriginalUrlForRedirect(String shortCode) {
        return linksMappingRepository.getOriginalUrlByShortCode(shortCode);
    }
}
