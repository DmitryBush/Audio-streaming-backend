package com.bush.user.controller;

import com.bush.user.dto.JwtTokenDto;
import com.bush.user.dto.UserChangePasswordDto;
import com.bush.user.dto.UserCreateDto;
import com.bush.user.dto.UserLoginDto;
import com.bush.user.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping(value = "/api/v1/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Validated UserLoginDto loginDto) {
        return ResponseEntity.ok(securityService.logIn(loginDto));
    }

    @PostMapping(value = "/api/v1/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody @Validated UserCreateDto createDto) {
        securityService.register(createDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/v1/change-password", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@RequestBody @Validated UserChangePasswordDto changePasswordDto) {
        securityService.changePassword(changePasswordDto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/api/v1/logout"))
                .build();
    }

    @PostMapping(value = "/api/v1/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDto> refreshToken() {
        return ResponseEntity.ok(securityService.refreshToken());
    }
}
