package com.kt.urlshortener.repositorys.impl;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.repositorys.LinksMappingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class LinksMappingRepositoryImpl implements LinksMappingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(LinksMapping linksMapping) {
        entityManager.persist(linksMapping);
    }

    @Override
    public String getOriginalUrlByShortCode(String shortCode) {
        String statement = """
                select e.originalLink from LinksMapping e where e.shortCode = :shortCode
                """;
        return entityManager.createQuery(statement,String.class)
                .setParameter("shortCode", shortCode)
                .getSingleResult();
    }
}
