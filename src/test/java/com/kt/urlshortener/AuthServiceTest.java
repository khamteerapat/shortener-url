package com.kt.urlshortener;

import com.kt.urlshortener.configs.JwtTokenProvider;
import com.kt.urlshortener.entities.LinksMapping;
import com.kt.urlshortener.entities.Users;
import com.kt.urlshortener.exceptions.GenerateShortUrlException;
import com.kt.urlshortener.exceptions.NotFoundUserException;
import com.kt.urlshortener.repositories.UsersRepository;
import com.kt.urlshortener.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthServiceImpl authService;

    //1.register success
    @Test
    void testRegisterSuccess(){
        String username = "teerapat@hotmail.com";
        String password = "22445566";
        String encryptPassword = "d32412d2d@$%#1";

        when(passwordEncoder.encode(password)).thenReturn(encryptPassword);

        String result = authService.register(username,password);

        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);

        verify(passwordEncoder,times(1)).encode(password);
        verify(usersRepository,times(1)).save(userCaptor.capture());
        assertEquals(encryptPassword,userCaptor.getValue().getPassword());
        assertEquals("Register Success.", result);
    }
    //2.register fail
    @Test
    void testRegisterFail(){
        String username = "teerapat@hotmail.com";
        String password = "22445566";
        String encryptPassword = "d32412d2d@$%#1";

        when(passwordEncoder.encode(password)).thenReturn(encryptPassword);

        String errorMsg = "duplicate key 30R2tBvFuxHC, org.springframework.dao.DataIntegrityViolationException: could not execute statement [ERROR: duplicate key value violates unique constraint \"links_mapping_short_code_key\" Detail: Key (short_code)=(6aOVYUOJlq5K) already exists.] [insert into links_mapping (created_at,created_by,original_link,short_code,updated_at,updated_by,id) values (?,?,?,?,?,?,?)]; SQL [insert into links_mapping (created_at,created_by,original_link,short_code,updated_at,updated_by,id) values (?,?,?,?,?,?,?)]; constraint [links_mapping_short_code_key]";
        DataIntegrityViolationException dupException = new DataIntegrityViolationException(errorMsg);
        doThrow(dupException).when(usersRepository).save(any(Users.class));

        assertThrows(DataIntegrityViolationException.class,
                () -> authService.register(username,password)
        );
    }

    //1. login success
    @Test
    void testLoginSuccess(){
        String username = "teerapat@hotmail.com";
        String password = "22445566";
        String encryptPassword = "d32412d2d@$%#1";

        Optional<Users> mockUserOpt = Optional.of(new Users(
                UUID.randomUUID(),
                username,
                encryptPassword,
                username,
                true,
                Instant.now(),
                null
        ));


        when(usersRepository.findByUsername(username)).thenReturn(mockUserOpt);
        when(passwordEncoder.matches(eq(password), eq(mockUserOpt.get().getPassword()))).thenReturn(true);

        String token = authService.login(username,password);


        verify(jwtTokenProvider,times(1)).generateJwtToken(username);
    }

    //2. user not found
    @Test
    void testUserNotFound(){
        String username = "teerapat@hotmail.com";
        String password = "22445566";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class,() -> authService.login(username,password));
    }

    //3. user password incorrect
    @Test
    void testUserPasswordNotMatch(){
        String username = "teerapat@hotmail.com";
        String password = "22445566";
        String encryptPassword = "d32412d2d@$%#1";

        Optional<Users> mockUserOpt = Optional.of(new Users(
                UUID.randomUUID(),
                username,
                encryptPassword,
                username,
                true,
                Instant.now(),
                null
        ));


        when(usersRepository.findByUsername(username)).thenReturn(mockUserOpt);
        when(passwordEncoder.matches(eq(password), eq(mockUserOpt.get().getPassword()))).thenReturn(false);


        assertThrows(BadCredentialsException.class,() -> authService.login(username,password));
    }
}
