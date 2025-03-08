package com.example.JWT_RestAPI.model;

import java.util.List;

public class Professor {
    private Long id;
    private Usuario usuario;
    private List<Disciplina> disciplinasLecionadas;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Disciplina> getDisciplinasLecionadas() { return disciplinasLecionadas; }
    public void setDisciplinasLecionadas(List<Disciplina> disciplinas) { this.disciplinasLecionadas = disciplinas; }
}