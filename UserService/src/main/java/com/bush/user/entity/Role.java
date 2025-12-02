package com.bush.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @Column(name = "role_id")
    private Short roleId;
    
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    // Constructors
    public Role() {}
    
    public Role(Short roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    // Getters and Setters
    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}