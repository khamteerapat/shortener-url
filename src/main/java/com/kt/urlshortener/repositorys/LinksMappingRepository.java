package com.kt.urlshortener.repositorys;

import com.kt.urlshortener.entities.LinksMapping;

public interface LinksMappingRepository  {
    void save(LinksMapping linksMapping);

    String getOriginalUrlByShortCode(String shortCode);
}
