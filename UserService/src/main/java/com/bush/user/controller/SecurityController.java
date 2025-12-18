package com.bush.user.controller;

import com.bush.user.dto.UserChangePasswordDto;
import com.bush.user.dto.UserCreateDto;
import com.bush.user.dto.UserLoginDto;
import com.bush.user.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(securityService.logIn(loginDto));
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDto createDto) {
        securityService.register(createDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePasswordDto changePasswordDto) {
        securityService.changePassword(changePasswordDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
