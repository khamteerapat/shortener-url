package com.kt.urlshortener.utils;

import com.kt.urlshortener.payloads.UserPrincipal;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class ApplicationContextUtils {
    public static UserPrincipal getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        }
        throw new AuthenticationCredentialsNotFoundException("not found user principal");
    }
}
