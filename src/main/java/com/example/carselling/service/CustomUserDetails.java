package com.example.carselling.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final Long userId;
    private final String email;
    private final String password;
    private final String firstname;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String email, String password, String firstname,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
