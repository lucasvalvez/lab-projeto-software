package com.example.JWT_RestAPI.model;

import com.example.JWT_RestAPI.enums.RoleEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotBlank(message = "Email é necessário")
    private String email;
    
    @NotBlank(message = "Senha é necessária")
    private String password;
    
    @NotNull(message = "Role é necessária")
    private RoleEnum role;

    public LoginRequest(String email, String password, RoleEnum role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}

