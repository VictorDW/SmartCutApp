package com.springsecurity.practica.Jwt;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,  
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
           
        final String token = getTokenFromRequest(request);
        final String userName;

        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        userName = jwtService.getUserNameFromToken(token);

        if (Objects.nonNull(userName) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtService.isValidToken(token, userDetails)) {
                var usernameAuthentication = new UsernamePasswordAuthenticationToken(
                                                    userDetails,
                                                    null,
                                                    userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernameAuthentication);
            }
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
