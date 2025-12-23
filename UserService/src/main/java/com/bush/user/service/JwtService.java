package com.bush.user.service;

import com.bush.user.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.security.jwt.secret-key}")
    private String signingKey;
    @Value("${spring.security.jwt.expiration}")
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration jwtExpiration;

    private final RedisTemplate<String, String> blackListTokenRedisTemplate;

    public String generateToken(UserDetails userDetails) {
        List<String> authorityList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .claim("role", authorityList)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public Map<String, Object> getMapPayload(String jwtToken) {
        return parseSignedToken(jwtToken).getPayload();
    }

    private Jws<Claims> parseSignedToken(String jwtToken) {
        if (jwtToken.equals(blackListTokenRedisTemplate.opsForValue()
                .get(SecurityConstants.BLACKLIST_KEY_PREFIX.getValue() + jwtToken))) {
            throw new IllegalArgumentException("Current token is located in blacklist");
        }
        JwtParser parser = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build();
        return parser.parseSignedClaims(jwtToken);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
