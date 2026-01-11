package com.bush.user.service.user;

import com.bush.user.dto.UserChangePasswordDto;
import com.bush.user.dto.UserCreateDto;
import com.bush.user.dto.UserReadDto;
import com.bush.user.entity.Role;
import com.bush.user.entity.RoleEnum;
import com.bush.user.repository.RoleRepository;
import com.bush.user.repository.UserRepository;
import com.bush.user.service.user.mapper.UserCreateMapper;
import com.bush.user.service.user.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "userTransactionManager")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;

    @Transactional("userTransactionManager")
    public void createUser(UserCreateDto dto) {
        Role role = roleRepository.getReferenceById(dto.roleId());
        Optional.of(dto)
                .map(userCreateMapper::mapToUser)
                .map(user -> {
                    user.setRole(role);
                    return user;
                })
                .map(userRepository::save);
    }

    @Transactional("userTransactionManager")
    public void updateUserRole(String userId, Short roleId) {
        userRepository.findById(userId)
                .map(user -> {
                    Optional.ofNullable(roleId)
                            .map(id -> {
                                if (user.getRole().getRoleName().equals(RoleEnum.ADMIN)
                                        && userRepository.countUserWithRole(RoleEnum.ADMIN) <= 1) {
                                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                                }
                                return id;
                            })
                            .map(roleRepository::getReferenceById)
                            .ifPresent(user::setRole);
                    return user;
                })
                .map(userRepository::save);
    }

    @Transactional("userTransactionManager")
    public void deleteUser(String userId) {
        String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        userRepository.findById(userId)
                .ifPresentOrElse(user -> {
                    if (user.getRole().getRoleName().equals(RoleEnum.ADMIN)
                            && userRepository.countUserWithRole(RoleEnum.ADMIN) <= 1) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    } else if (!userDetails.equals(user.getLogin())
                            && !authorities.contains(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN.name()))) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                    userRepository.delete(user);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    @Transactional("userTransactionManager")
    public void changeUserPassword(UserChangePasswordDto dto) {
        userRepository.findByLogin(dto.login())
                .map(user -> {
                    if (passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(dto.newPassword()));
                        user.setPasswordVersion(user.getPasswordVersion() + 1);
                        return user;
                    }
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                })
                .map(userRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public boolean checkEqualPasswordVersion(String username, Long passwordVersion) {
        return Optional.ofNullable(username)
                .map(userRepository::findByLogin)
                .map(optionalUser -> optionalUser
                        .map(user -> user.getPasswordVersion().equals(passwordVersion))
                        .orElseThrow(IllegalArgumentException::new))
                .orElseThrow(IllegalArgumentException::new);
    }

    public UserReadDto findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userReadMapper::mapToUserReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(user -> new User(user.getLogin(), user.getPassword(), List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().name()))))
                .orElseThrow(() -> new UsernameNotFoundException("Error in username or password"));
    }
}
