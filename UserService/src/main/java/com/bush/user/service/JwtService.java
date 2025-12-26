package com.bush.user.service;

import com.bush.user.config.security.SecurityConstants;
import com.bush.user.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserService userService;
    private final RedisTemplate<String, String> blackListTokenRedisTemplate;
    @Value("${spring.security.jwt.secret-key}")
    private String signingKey;
    @Value("${spring.security.jwt.expiration}")
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration jwtExpiration;

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

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
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
        Jws<Claims> claimsJws = parser.parseSignedClaims(jwtToken);
        checkPasswordVersionValidity(jwtToken, claimsJws);
        return claimsJws;
    }

    private void checkPasswordVersionValidity(String jwtToken, Jws<Claims> claimsJws) {
        if (claimsJws.getPayload().containsKey("version")) {
            Long passwordVersion = claimsJws.getPayload().get("version", Long.class);
            if (!userService.checkEqualPasswordVersion(claimsJws.getPayload().getSubject(), passwordVersion)) {
                blackListTokenRedisTemplate.opsForValue()
                        .set(SecurityConstants.BLACKLIST_KEY_PREFIX.getValue() + jwtToken, jwtToken);
                blackListTokenRedisTemplate.expire(SecurityConstants.BLACKLIST_KEY_PREFIX.getValue() + jwtToken,
                        15, TimeUnit.MINUTES);
                throw new IllegalArgumentException("Password version does not match the valid one");
            }
        }
    }
}
