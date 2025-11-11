package com.kt.urlshortener.repositorys;

import com.kt.urlshortener.entities.LinksMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface LinksMappingJpaRepository extends JpaRepository<LinksMapping, UUID>, JpaSpecificationExecutor<LinksMapping> {
}
