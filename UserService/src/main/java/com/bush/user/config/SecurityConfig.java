package com.bush.user.config;

import com.bush.user.entity.RoleEnum;
import com.bush.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/api/*/login", "/api/*/register", "/api/*/logout").permitAll()
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
                .logout(logout -> logout.logoutUrl("/api/v1/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(
                new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations));
        return authenticationProvider;
    }
}
