package com.puc.backend.model.requests;

import com.puc.backend.model.Usuario.TipoUsuario;

public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}