package com.campus.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

/**
 * JWT 工具类（规范文档 4.2 / 十二.8）。
 *
 * <p>Token 中只保存 userId 和 username，后端从 Token 中识别当前用户，
 * 不信任前端传入的 userId（规范文档 12.1 第三点）。</p>
 */
@Component
public class JwtUtil {

    private static final String CLAIM_USER_ID = "userId";

    private static final String CLAIM_USERNAME = "username";

    private final SecretKey secretKey;

    private final Duration expiration;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-hours:24}") long expirationHours) {
        // HS256 要求密钥长度不少于 256 bit（32 字节）
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = Duration.ofHours(expirationHours);
    }

    /**
     * 生成登录凭证。
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expiration.toMillis());
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(CLAIM_USER_ID, userId)
                .claim(CLAIM_USERNAME, username)
                .issuedAt(now)
                .expiration(expireAt)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 Token；非法或过期时抛出 {@link JwtException}。
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从 Token 中取出用户 ID。
     */
    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        Number userId = claims.get(CLAIM_USER_ID, Number.class);
        return userId == null ? null : userId.longValue();
    }

    /**
     * 从 Token 中取出用户名。
     */
    public String getUsername(String token) {
        return parseToken(token).get(CLAIM_USERNAME, String.class);
    }
}
