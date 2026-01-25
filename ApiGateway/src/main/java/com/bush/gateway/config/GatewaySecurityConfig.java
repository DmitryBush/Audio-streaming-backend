package com.bush.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/error").permitAll()
                        .pathMatchers("/api/*/login", "/api/*/register", "/api/*/logout").permitAll()
                        .pathMatchers("/api/*/change-password", "/api/*/refresh-token").authenticated()
                        .pathMatchers(HttpMethod.PATCH, "/api/*/users/*/role").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers("/api/*/users/**").authenticated()
                        .pathMatchers("/api/*/security/**").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers("/api/*/playlists/**").authenticated()
                        .pathMatchers("/api/*/uploads/**").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers(HttpMethod.PATCH, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers(HttpMethod.PUT, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers(HttpMethod.DELETE, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .pathMatchers("/api/*/songs/*", "/api/*/albums/*", "/api/*/artists/*",
                                "/api/*/genres/*", "/api/*/streaming/*").authenticated()
                        .pathMatchers("/api/*/search/*").authenticated()
                        .anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .build();
    }
}
