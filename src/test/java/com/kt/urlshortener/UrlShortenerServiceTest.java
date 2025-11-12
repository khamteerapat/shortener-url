package com.kt.urlshortener;

import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.exceptions.GenerateShortUrlException;
import com.kt.urlshortener.payloads.UserPrincipal;
import com.kt.urlshortener.repositories.LinksMappingRepository;
import com.kt.urlshortener.services.impl.UrlShortenerServiceImpl;
import com.kt.urlshortener.utils.ApplicationContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {
    private static final String FULL_URL_MOCK = "https://www.google.com/search?q=springboot";
    private final String username = "teerapat@hotmail.com";
    @Mock
    private LinksMappingRepository linksMappingRepository;
    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerService;

    private MockedStatic<ApplicationContextUtils> mockedStatic;
    @BeforeEach
    void setup() {
        UserPrincipal userPrincipal = new UserPrincipal(username,username);
        mockedStatic = mockStatic(ApplicationContextUtils.class);
        mockedStatic.when(ApplicationContextUtils::getUserInfo).thenReturn(userPrincipal);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close(); // ปิด static mock หลังแต่ละ test
    }

    //1.case generate short url success
    @Test
    void testGenerateSuccess(){
        UserPrincipal userPrincipal = new UserPrincipal(username,username);

        doNothing().when(linksMappingRepository).save(any(LinksMapping.class));

        String shortUrl = urlShortenerService.generateShortUrl(FULL_URL_MOCK);

        assertEquals(36,shortUrl.length());

    }

    //2.case can't save links_mapping because duplicate key and 5 retries save
    @Test
    void testDuplicateShortCode(){
        String expectsMsg = "Failed to generate unique short URL after 5 retries";


        String errorMsg = "duplicate key 30R2tBvFuxHC, org.springframework.dao.DataIntegrityViolationException: could not execute statement [ERROR: duplicate key value violates unique constraint \"links_mapping_short_code_key\" Detail: Key (short_code)=(6aOVYUOJlq5K) already exists.] [insert into links_mapping (created_at,created_by,original_link,short_code,updated_at,updated_by,id) values (?,?,?,?,?,?,?)]; SQL [insert into links_mapping (created_at,created_by,original_link,short_code,updated_at,updated_by,id) values (?,?,?,?,?,?,?)]; constraint [links_mapping_short_code_key]";
        DataIntegrityViolationException dupException = new DataIntegrityViolationException(errorMsg);
        doThrow(dupException).when(linksMappingRepository)
                .save(any(LinksMapping.class));

        GenerateShortUrlException exception = assertThrows(GenerateShortUrlException.class,
                () -> urlShortenerService.generateShortUrl(FULL_URL_MOCK)
                );

        assertTrue(exception.getMessage().contains(expectsMsg));
        verify(linksMappingRepository,times(5)).save(any(LinksMapping.class));
    }

    //3.case can't save with other exception
    @Test
    void testHandleException(){
        String expectsMsg = "Unexpected error occurred during short URL generation";

        doThrow(RuntimeException.class).when(linksMappingRepository).save(any(LinksMapping.class));

        GenerateShortUrlException exception = assertThrows(GenerateShortUrlException.class,
                () -> urlShortenerService.generateShortUrl(FULL_URL_MOCK)
                );

        assertTrue(exception.getMessage().contains(expectsMsg));
    }
}
