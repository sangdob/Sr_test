package com.SrBackend.jwt;

import com.SrBackend.config.JwtConfig;
import com.SrBackend.security.CustomUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    public static final String BEARER = "Bearer ";
    public static final String REFRESH_TOKEN = "refresh_token";

    private final JwtConfig jwtConfig;

    public String createAccessToken(HttpServletRequest request, CustomUserDetails details) {
        return BEARER + JWT.create()
            .withSubject(details.getUsername())
            .withIssuer(request.getRequestURI().toString())
            .withIssuedAt(Date.from(Instant.now()))
            .withExpiresAt(Date.from(
                Instant.now().plusSeconds(Long.parseLong(jwtConfig.getAccessTokenExpire()))))
            .sign(jwtConfig.getEncodedSecretKey());
    }

    public String createRefreshToken(HttpServletRequest request, String subject) {
        return JWT.create()
            .withSubject(subject)
            .withIssuer(request.getRequestURI().toString())
            .withIssuedAt(Date.from(Instant.now()))
            .withExpiresAt(Date.from(Instant.now().plusSeconds(Long.parseLong(jwtConfig.getRefreshTokenExpire()))))
            .sign(jwtConfig.getEncodedSecretKey());
    }

    public JWTInfo decodeToken(String token)
        throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(jwtConfig.getEncodedSecretKey()).build();

        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();

        return JWTInfo.builder()
            .username(username)
            .build();
    }

    public boolean isCookieNameRefreshToken(Cookie cookie) {
        return JwtTokenProvider.REFRESH_TOKEN.equals(cookie.getName());
    }

    @Getter
    @Builder
    @ToString
    public static class JWTInfo {
        private final String username;
    }
}
