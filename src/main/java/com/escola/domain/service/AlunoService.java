package com.escola.domain.service;

import java.util.List;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;

public interface AlunoService {

    Aluno create(Aluno alunoToCreate) throws Exception;

    Aluno findById(Long id);

    Aluno findByName(String nome);

    List<Aluno> findBySerie(String serie) throws CustomException;

    List<Aluno> getAll();

    //TODO: Incluir o Endereco
    Aluno update(Long id, String nome, String dataNascimento, String serie, String segmento, String nomeMae, String nomePai);

    Aluno delete(Long id);

}
