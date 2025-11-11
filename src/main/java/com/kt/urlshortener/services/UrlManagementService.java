package com.kt.urlshortener.services;

import com.kt.urlshortener.payloads.PageResponse;
import com.kt.urlshortener.payloads.UrlViewsResponsePayload;
import org.springframework.data.domain.Sort;

import java.util.UUID;


public interface UrlManagementService {
    PageResponse<UrlViewsResponsePayload> getUrlListByCreatedBy(int page, int limit, Sort.Direction direction);

    String deleteUrlById(UUID id);
}
