package com.kt.urlshortener.repositorys;

import com.kt.urlshortener.entities.LinksMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface LinksMappingRepository  {
    void save(LinksMapping linksMapping);

    String getOriginalUrlByShortCode(String shortCode);
    Page<LinksMapping> getListByCreatedBy(String createdBy, int page, int limit, Sort.Direction direction);
    void deleteById(UUID id);
}
