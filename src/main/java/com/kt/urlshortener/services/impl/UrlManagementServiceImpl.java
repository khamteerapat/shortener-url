package com.kt.urlshortener.services.impl;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.payloads.PageResponse;
import com.kt.urlshortener.payloads.UrlViewsResponsePayload;
import com.kt.urlshortener.payloads.UserPrincipal;
import com.kt.urlshortener.repositories.LinksMappingRepository;
import com.kt.urlshortener.services.UrlManagementService;
import com.kt.urlshortener.utils.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlManagementServiceImpl implements UrlManagementService {
    private final LinksMappingRepository linksMappingRepository;
    @Override
    public PageResponse<UrlViewsResponsePayload> getUrlListByCreatedBy(int page, int limit, Sort.Direction direction) {
        UserPrincipal userPrincipal = ApplicationContextUtils.getUserInfo();

        Page<LinksMapping> pageLinkMappings = linksMappingRepository.getListByCreatedBy(userPrincipal.getUsername(),page,limit,direction);
        List<UrlViewsResponsePayload> linksMappingList = pageLinkMappings.getContent()
                .stream().map(linksMapping ->
                        new UrlViewsResponsePayload(
                                linksMapping.getId(),
                                linksMapping.getShortCode(),
                                linksMapping.getOriginalLink(),
                                linksMapping.getCreatedAt()
                        )).toList();
        return new PageResponse<>(
                linksMappingList,
                pageLinkMappings.getNumber() + 1, // แปลงกลับให้เริ่มที่หน้า 1
                pageLinkMappings.getTotalPages(),
                pageLinkMappings.getTotalElements(),
                pageLinkMappings.hasNext(),
                pageLinkMappings.hasPrevious()
        );
    }

    @Override
    public String deleteUrlById(UUID id) {
        linksMappingRepository.deleteById(id);
        return "delete success.";
    }
}
