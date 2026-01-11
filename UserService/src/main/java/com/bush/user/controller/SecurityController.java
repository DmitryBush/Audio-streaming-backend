package com.bush.user.controller;

import com.bush.user.dto.JwtTokenDto;
import com.bush.user.dto.UserChangePasswordDto;
import com.bush.user.dto.UserCreateDto;
import com.bush.user.dto.UserLoginDto;
import com.bush.user.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/api/v1/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Validated UserLoginDto loginDto) {
        return ResponseEntity.ok(securityService.logIn(loginDto));
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<Void> register(@RequestBody @Validated UserCreateDto createDto) {
        securityService.register(createDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated UserChangePasswordDto changePasswordDto) {
        securityService.changePassword(changePasswordDto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/api/v1/logout"))
                .build();
    }

    @PostMapping("/api/v1/refresh-token")
    public ResponseEntity<JwtTokenDto> refreshToken() {
        return ResponseEntity.ok(securityService.refreshToken());
    }
}
