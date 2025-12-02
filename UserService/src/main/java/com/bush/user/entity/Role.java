package com.bush.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    
    @Id
    @Column(name = "role_id")
    private Short roleId;
    
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}