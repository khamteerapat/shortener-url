package com.kt.urlshortener;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.payloads.PageResponse;
import com.kt.urlshortener.payloads.UrlViewsResponsePayload;
import com.kt.urlshortener.payloads.UserPrincipal;
import com.kt.urlshortener.repositories.LinksMappingRepository;
import com.kt.urlshortener.services.impl.UrlManagementServiceImpl;
import com.kt.urlshortener.utils.ApplicationContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UrlManagementServiceTest {

    @Mock
    private LinksMappingRepository linksMappingRepository;
    @InjectMocks
    private UrlManagementServiceImpl urlManagementService;

    private MockedStatic<ApplicationContextUtils> mockedStatic;
    @BeforeEach
    void setup() {
        String username = "teerapat@hotmail.com";
        UserPrincipal userPrincipal = new UserPrincipal(username, username);
        mockedStatic = mockStatic(ApplicationContextUtils.class);
        mockedStatic.when(ApplicationContextUtils::getUserInfo).thenReturn(userPrincipal);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close(); // ปิด static mock หลังแต่ละ test
    }


    List<LinksMapping> mockLinksMappingList = List.of(
            new LinksMapping(
                    UUID.randomUUID(),
                    "abc123",
                    "https://google.com",
                    Instant.now(),
                    "art",
                    Instant.now(),
                    "admin"
            ),
            new LinksMapping(
                    UUID.randomUUID(),
                    "xyz789",
                    "https://github.com",
                    Instant.now(),
                    "john",
                    Instant.now(),
                    "john"
            ),
            new LinksMapping(
                    UUID.randomUUID(),
                    "pqr456",
                    "https://stackoverflow.com",
                    Instant.now(),
                    "mike",
                    Instant.now(),
                    "admin"
            )
    );
    //1.success
    @Test
    void testViewsSuccess(){
        String username = "teerapat@hotmail.com";
        int page = 1;
        int limit = 10;
        Sort.Direction direction = Sort.Direction.ASC;

        Page<LinksMapping> mockpage = new PageImpl<>(mockLinksMappingList);

        when(linksMappingRepository.getListByCreatedBy(username,page,limit,direction)).thenReturn(mockpage);

        PageResponse<UrlViewsResponsePayload> result = urlManagementService.getUrlListByCreatedBy(page,limit,direction);
        assertNotNull(result);
        assertEquals(1, result.getCurrentPage());
        assertEquals(mockpage.getTotalPages(), result.getTotalPages());
        assertEquals(mockpage.getTotalElements(), result.getTotalElements());
        assertEquals(3, result.getContent().size());

    }

    @Test
    void testDeleteSuccess() {

        UUID mockId = UUID.randomUUID();

        String result = urlManagementService.deleteUrlById(mockId);

        assertEquals("delete success.", result);

        verify(linksMappingRepository, times(1)).deleteById(mockId);
    }
}
