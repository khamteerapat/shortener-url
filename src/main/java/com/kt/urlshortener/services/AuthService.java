package com.kt.urlshortener.services;

public interface AuthService {
    String register(String username, String password);
    String login(String username,String password);
}
