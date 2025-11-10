package com.kt.urlshortener.repositorys;

import com.kt.urlshortener.entities.LinksMapping;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LinksMappingRepository  {
    void save(LinksMapping linksMapping);
}
