package com.SrBackend.security.filter;

import com.SrBackend.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!authorizationHeader.isBlank() && authorizationHeader.startsWith(BEARER)) {
            token = getToken(authorizationHeader);
        }

        if (token != null) {

        }

    }

    private String getToken(String tokenHeader) {
        return tokenHeader.substring(BEARER.length());
    }

}
