package com.kt.urlshortener.controllers;

import com.kt.urlshortener.payloads.PageResponse;
import com.kt.urlshortener.payloads.UrlViewsResponsePayload;
import com.kt.urlshortener.services.UrlManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlManagementController {
    private final UrlManagementService urlManagementService;

    @GetMapping
    public ResponseEntity<PageResponse<UrlViewsResponsePayload>> viewsUrls(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "limit",defaultValue = "100") Integer limit,
            @RequestParam(name = "direction",defaultValue = "ASC") String direction){
        Sort.Direction filterDir = Sort.Direction.ASC;
        if(direction != null && direction.equals("DESC")) filterDir = Sort.Direction.DESC;

        return ResponseEntity.ok(urlManagementService.getUrlListByCreatedBy(page, limit, filterDir));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUrlById(@PathVariable UUID id){
        return ResponseEntity.ok(urlManagementService.deleteUrlById(id));
    }
}
