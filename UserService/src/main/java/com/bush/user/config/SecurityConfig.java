package com.bush.user.config;

import com.bush.user.entity.RoleEnum;
import com.bush.user.filter.JwtFilter;
import com.bush.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuration parameters for Argon2
    @Value("${spring.security.encryption-config.salt-length}")
    private Integer saltLength;
    @Value("${spring.security.encryption-config.hash-length}")
    private Integer hashLength;
    @Value("${spring.security.encryption-config.parallelism}")
    private Integer parallelism;
    @Value("${spring.security.encryption-config.memory}")
    private Integer memory;
    @Value("${spring.security.encryption-config.iterations}")
    private Integer iterations;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/actuator/**", "/error").permitAll()
                        .requestMatchers("/api/*/login", "/api/*/register", "/api/*/logout").permitAll()
                        .requestMatchers("/api/*/change-password").fullyAuthenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/*/users/*/role").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/api/*/users/**").authenticated()
                        .requestMatchers("/api/*/security/**").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/api/*/playlists/**").authenticated()
                        .requestMatchers("/api/*/uploads/**").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/*/songs/*", "/api/*/albums/*",
                                "/api/*/artists/*", "/api/*/genres/*", "/api/*/streaming/*").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/api/*/songs/*", "/api/*/albums/*", "/api/*/artists/*",
                                "/api/*/genres/*", "/api/*/streaming/*").authenticated()
                        .requestMatchers("/api/*/search/*").authenticated()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout.logoutUrl("/api/v1/logout")
                        .deleteCookies("REFRESH_TOKEN")
                        .logoutSuccessHandler((request, response, authentication)
                                -> SecurityContextHolder.clearContext()))
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
