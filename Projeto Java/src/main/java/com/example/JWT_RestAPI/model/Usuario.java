package com.example.JWT_RestAPI.model;

import com.example.JWT_RestAPI.enums.RoleEnum;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private RoleEnum role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public RoleEnum getRole() { return role; }
    public void setRole(RoleEnum role) { this.role = role; }
}