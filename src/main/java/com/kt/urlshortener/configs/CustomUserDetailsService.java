package com.kt.urlshortener.configs;

import com.kt.urlshortener.entities.Users;
import com.kt.urlshortener.payloads.UserPrincipal;
import com.kt.urlshortener.repositorys.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Users findUser = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
        return new UserPrincipal();
    }
}
