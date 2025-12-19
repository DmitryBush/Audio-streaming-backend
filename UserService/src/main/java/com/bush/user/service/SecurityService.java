package com.bush.user.service;

import com.bush.user.dto.UserChangePasswordDto;
import com.bush.user.dto.UserCreateDto;
import com.bush.user.dto.UserLoginDto;
import com.bush.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public String logIn(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.login(), loginDto.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken((UserDetails) authentication.getPrincipal());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public void register(UserCreateDto createDto) {
        UserCreateDto encryptedUserCredentials =
                new UserCreateDto(createDto.login(), passwordEncoder.encode(createDto.password()), createDto.roleId());
        userService.createUser(encryptedUserCredentials);
    }

    public void changePassword(UserChangePasswordDto changePasswordDto) {
        userService.changeUserPassword(changePasswordDto);
    }
}
