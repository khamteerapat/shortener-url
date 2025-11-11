package com.kt.urlshortener.services.impl;

import com.kt.urlshortener.configs.JwtTokenProvider;
import com.kt.urlshortener.entities.Users;
import com.kt.urlshortener.exceptions.NotFoundUserException;
import com.kt.urlshortener.repositorys.UsersRepository;
import com.kt.urlshortener.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String REGISTER_SUCCESS = "Register Success.";
    private static final String LOGIN_SUCCESS = "Login Success.";

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String register(String username, String password){
        String encryptPassword = passwordEncoder.encode(password);

        Users users = new Users(
                username,
                encryptPassword,
                username
        );
        usersRepository.save(users);
        return LOGIN_SUCCESS;
    }

    @Override
    public String login(String username, String password) {
        Users findUser = usersRepository.findByUsername(username).orElse(null);
        if(findUser == null) throw new NotFoundUserException("User not found");

        boolean isMatch = passwordEncoder.matches(password,findUser.getPassword());
        if(!isMatch) throw new BadCredentialsException("Authentication Fail.");
        return jwtTokenProvider.generateJwtToken(username);
    }
}
