package com.escola.domain.service;

import java.util.List;

import com.escola.domain.model.Aluno;

public interface AlunoService {

    Aluno create(Aluno alunoToCreate);

    Aluno findById(Long id);

    Aluno findByName(String nome);

    List<Aluno> findBySerie(String serie);

    List<Aluno> getAll();

    Aluno update(Long id);

    Aluno delete(Long id);

}
