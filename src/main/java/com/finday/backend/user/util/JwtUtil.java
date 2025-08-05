package com.finday.backend.user.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key secretKey;
    private final long expirationMs = 86400000; // 1일

    @PostConstruct
    public void init() {
        // @Value가 주입된 이후에 키 초기화
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ 토큰 생성
    public String generateToken(Long userNo) {
        return Jwts.builder()
                .setSubject(String.valueOf(userNo))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰 유효성 검증 및 userNo 추출
    public Long validateAndGetUserNo(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }
}
