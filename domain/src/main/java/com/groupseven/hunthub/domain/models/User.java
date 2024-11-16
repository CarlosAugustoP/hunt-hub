package com.groupseven.hunthub.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User implements UserDetails {

    public UserId id;

    public String cpf;

    public int points = 0;

    public String name;

    public String email;

    public String password;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null && !this.role.isEmpty()) {
            return List.of(new SimpleGrantedAuthority(this.role));
        } else {
            throw new IllegalStateException("User role cannot be null or empty");
        }
    }

    public User(String name, String email, String password, String cpf, String role, UserId id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.id = id;
        this.role = role;
    }

    public User() {
    }

    public UserId getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    protected void setId(UUID id) {
        this.id = new UserId(id);
    }
}
