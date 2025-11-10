package com.kt.urlshortener.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "links_mapping")
public class LinksMapping {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "short_code", unique = true, nullable = false)
    private String shortCode;

    @Column(name = "original_link", columnDefinition = "TEXT", nullable = false)
    private String originalLink;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public LinksMapping(String shortCode, String originalLink, String createdBy, Instant createdAt) {
        this.shortCode = shortCode;
        this.originalLink = originalLink;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
