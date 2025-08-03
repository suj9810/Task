package com.example.task.common.jwt;

import com.example.task.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-time}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration-time}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(User user) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("userRole", user.getUserRole().name())
                .issuedAt(new Date())
                .expiration(new Date(now + accessTokenExpiration))
                .signWith(secretKey, SIG.HS512)
                .compact();
    }

    public String createRefreshToken(User user) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(now))
                .expiration(new Date(now + refreshTokenExpiration))
                .signWith(secretKey, SIG.HS512)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token){
        try {
            JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
            parser.parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비었습니다.");
        }
        return false;
    }

    public Long getUserIdFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        Jws<Claims> claimsJws = parser.parseSignedClaims(token);

        String subject = claimsJws.getPayload().getSubject();

        return Long.parseLong(subject);
    }

    public long getRemainingExpiration(String token) {
        Date expiration = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.getTime() - System.currentTimeMillis();
    }
}
