package com.bush.user.repository;

import com.bush.user.entity.RoleEnum;
import com.bush.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByLogin(String login);
    
    Boolean existsByLogin(String login);

    @Query("select count(u) from User u where u.role.roleName = :roleEnum")
    Long countUserWithRole(@Param("roleEnum") RoleEnum roleEnum);
}