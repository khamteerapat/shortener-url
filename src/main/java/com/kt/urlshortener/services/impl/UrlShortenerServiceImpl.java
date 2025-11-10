package com.kt.urlshortener.services.impl;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.exceptions.GenerateShortUrlException;
import com.kt.urlshortener.repositorys.LinksMappingRepository;
import com.kt.urlshortener.services.UrlShortenerService;
import com.kt.urlshortener.utils.Base62Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private static final int MAX_RETRY = 5;
    private static final int MAX_SHORT_CODE = 12;
    private static final String URL_TEMPLATE = "http://localhost:8080/r/%s";

    private final LinksMappingRepository linksMappingRepository;

    @Override
    public String generateShortUrl(String fullUrl) {
        for(int i = 0; i < MAX_RETRY ; i++){
            String shortCode = Base62Utils.generateShortCode(MAX_SHORT_CODE );
            try{
                LinksMapping linksMapping = new LinksMapping(
                        shortCode,
                        fullUrl,
                        "",
                        Instant.now()
                );
                linksMappingRepository.save(linksMapping);
                return String.format(URL_TEMPLATE,shortCode);
            }catch (DataIntegrityViolationException duplicateKeyException){
                if(duplicateKeyException.getMessage().contains("duplicate key")) {
                    log.error("generateShortUrl error : duplicate key {}, {}", shortCode, duplicateKeyException.toString());
                }else {
                    log.error("generateShortUrl error : {}", duplicateKeyException.toString());
                    throw duplicateKeyException;
                }

            }catch (Exception ex){
                log.error("generateShortUrl error : while generating short URL", ex);
                throw new GenerateShortUrlException("Unexpected error occurred during short URL generation", ex);
            }
        }
        throw new GenerateShortUrlException("generateShortUrl error : Failed to generate unique short URL after " + MAX_RETRY + " retries");
    }
}
