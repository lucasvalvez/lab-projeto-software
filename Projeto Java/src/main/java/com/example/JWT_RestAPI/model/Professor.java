package com.example.JWT_RestAPI.model;
import com.exceptions.DisciplinaNaoEncontradaException;

import java.util.List;

public class Professor {

    private List<Disciplina> disciplinasLecionadas;


    public List<Aluno> acessarAlunosMatriculados(Disciplina disciplina) throws DisciplinaNaoEncontradaException {

        for (Disciplina procurado : disciplinasLecionadas) {

            if (procurado.equals(disciplina)) {
                return procurado.getAlunos();
            }
        }

        throw new DisciplinaNaoEncontradaException("Disciplina não encontrada.");
    }
}