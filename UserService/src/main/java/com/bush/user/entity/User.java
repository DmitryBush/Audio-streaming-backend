package com.bush.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    
    @Column(name = "role_id", nullable = false)
    private Short roleId;
    
    @Column(name = "password", nullable = false)
    private String password;
}