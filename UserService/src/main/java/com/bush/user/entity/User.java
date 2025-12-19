package com.bush.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @Column(name = "password", nullable = false)
    private String password;
}