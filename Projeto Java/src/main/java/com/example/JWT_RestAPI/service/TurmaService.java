package com.example.JWT_RestAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JWT_RestAPI.model.Turma;
import com.example.JWT_RestAPI.repository.TurmaRepository;

@Service
public class TurmaService {
    @Autowired
    TurmaRepository turmaRepository;
    
    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }
}
