package com.kt.urlshortener.configs;

import com.kt.urlshortener.payloads.UserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("x-authorization");
        String token;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);

            try{
                String username = jwtTokenProvider.getUsernameToken(token);

                Claims claims = jwtTokenProvider.getClaimsToken(token);
                String email = claims.get("email").toString();

                if(username != null && !jwtTokenProvider.isExpiredToken(token)){
                    UserPrincipal userPrincipal = new UserPrincipal(username,email);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userPrincipal,null,List.of()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (Exception ex){
                log.warn("Invalid JWT Token: {}", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Invalid token\"}");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
