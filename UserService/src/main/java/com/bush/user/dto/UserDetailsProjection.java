package com.bush.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserDetailsProjection implements UserDetails {
    private String username;
    private Collection<? extends GrantedAuthority> roles;

    public UserDetailsProjection(UserDetails userDetails) {
        this.username = userDetails.getUsername();
        this.roles = userDetails.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }
}
