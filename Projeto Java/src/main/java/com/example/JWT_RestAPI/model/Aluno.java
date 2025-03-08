package com.example.JWT_RestAPI.model;

import java.util.List;

public class Aluno {
    private Long id;
    private String matricula;
    private Usuario usuario;
    private List<Disciplina> disciplinasMatriculadas;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Disciplina> getDisciplinasMatriculadas() { return disciplinasMatriculadas; }
    public void setDisciplinasMatriculadas(List<Disciplina> disciplinas) { this.disciplinasMatriculadas = disciplinas; }
}