package com.bush.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
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

    // Constructors
    public User() {}
    
    public User(String login, Short roleId, String password) {
        this.login = login;
        this.roleId = roleId;
        this.password = password;
    }
    
    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}