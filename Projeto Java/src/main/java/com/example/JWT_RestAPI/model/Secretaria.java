package com.example.JWT_RestAPI.model;

public class Secretaria {
    private Long id;
    private Usuario usuario;
    private String setor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}