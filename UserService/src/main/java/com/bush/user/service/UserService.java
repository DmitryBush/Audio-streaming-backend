package com.bush.user.service;

import com.bush.user.dto.UserCreateDto;
import com.bush.user.entity.Role;
import com.bush.user.entity.RoleEnum;
import com.bush.user.repository.RoleRepository;
import com.bush.user.repository.UserRepository;
import com.bush.user.service.mapper.UserCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserCreateMapper userCreateMapper;

    @Transactional
    public void createUser(UserCreateDto dto) {
        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Optional.of(dto)
                .map(userCreateMapper::mapToUser)
                .map(user -> {
                    user.setRole(role);
                    return user;
                })
                .map(userRepository::save);
    }

    @Transactional
    public void updateUserInfo(String userId, UserCreateDto dto) {
        userRepository.findById(userId)
                .map(user -> {
                    Optional.ofNullable(dto.roleId())
                            .map(roleRepository::findById)
                            .ifPresent(role -> {
                                user.setRole(role.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
                            });
                    return user;
                });
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(user -> {
                    if (user.getRole().getRoleName().equals(RoleEnum.ADMIN)
                            && userRepository.countUserWithRole(RoleEnum.ADMIN) <= 1) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                    userRepository.delete(user);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }
}
