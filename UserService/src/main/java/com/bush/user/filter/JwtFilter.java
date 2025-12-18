package com.bush.user.filter;

import com.bush.user.service.JwtService;
import com.bush.user.service.type.converter.TypeConverterService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final TypeConverterService typeConverterService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        Map<String, Object> jwtClaims = new HashMap<>();

        try {
            String jwtToken = authorizationHeader.substring(7);
            jwtClaims = jwtService.getMapPayload(jwtToken);
        } catch (UnsupportedJwtException e) {
            log.debug("Specified token is corrupted");
        } catch (JwtException e) {
            log.debug("Specified token is not supported");
        } catch (IllegalArgumentException e) {
            log.debug("Specified token is invalid");
        } catch (RuntimeException e) {
            log.debug("An error has occurred while parsing token");
        }

        if (Objects.nonNull(jwtClaims.get("sub")) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtClaims.get("sub"),
                    null,
                    typeConverterService.convertObjectToList(jwtClaims.get("role"), String.class)
                            .stream().map(SimpleGrantedAuthority::new).toList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
