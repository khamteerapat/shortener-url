package com.kt.urlshortener.repositorys.impl;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.repositorys.LinksMappingJpaRepository;
import com.kt.urlshortener.repositorys.LinksMappingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LinksMappingRepositoryImpl implements LinksMappingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final LinksMappingJpaRepository linksMappingJpaRepository;

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

    @Override
    public Page<LinksMapping> getListByCreatedBy(String createdBy, int page, int limit, Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by(direction,"createdAt"));

        Specification<LinksMapping> filter =
                ((root, query, criteriaBuilder)
                        -> criteriaBuilder.equal(root.get("createdBy"),createdBy)
                );

        return  linksMappingJpaRepository.findAll(filter,pageRequest);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        linksMappingJpaRepository.deleteById(id);
    }
}
