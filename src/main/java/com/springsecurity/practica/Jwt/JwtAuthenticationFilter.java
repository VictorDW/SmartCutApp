package com.springsecurity.practica.Jwt;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,  
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
           
        final String token = getTokenFromRequest(request);

        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
           // return authHeader.substring(7);
           return authHeader.replace("Bearer ", "");
        }

        return null;
    }


}
