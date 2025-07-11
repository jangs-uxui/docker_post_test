package com.example.post_project_test.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    // 토큰생성
    public String generateToken(String category, String username, String role, Long exp) {
        return Jwts.builder() // JWT생성
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발급시간 = 현재시간(밀리초)
                .expiration(new Date(System.currentTimeMillis() + exp)) // 만료시간 = 현재시간+유효기간
                .signWith(this.secretKey)
                .compact();
    }

    // 토큰에서 인증정보 추출
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload().get("username").toString();
    }
    public String getRole(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload().get("role").toString();
    }
    public String getCategory(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload().get("category").toString();
    }
    // 만료여부 확인
    public Boolean isTokenExpired(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload().getExpiration().before(new Date()); // 오늘보다 이전이면 true
    }





}
