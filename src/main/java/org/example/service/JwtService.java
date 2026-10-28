package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@Profile("security")
public class JwtService {
    private final SecretKey signingKey;
    private final long accessTokenSeconds;

    public JwtService(
            @Value("${pretium.security.jwt.secret}") String secret,
            @Value("${pretium.security.jwt.access-token-seconds:900}") long accessTokenSeconds) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("pretium.security.jwt.secret deve ter pelo menos 32 bytes");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenSeconds = accessTokenSeconds;
    }

    public String generateAccessToken(AppUser user) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(user.getUsername())
            .claim("role", user.getAuthorities().iterator().next().getAuthority())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessTokenSeconds)))
            .signWith(signingKey)
            .compact();
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isValid(String token, AppUser user) {
        try {
            Claims claims = parseClaims(token);
            return claims.getSubject().equals(user.getUsername())
                && claims.getExpiration().after(new Date())
                && user.isEnabled();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public long getAccessTokenSeconds() {
        return accessTokenSeconds;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}

