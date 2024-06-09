package com.escola.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escola.domain.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findFirstByNome(String nome);

    Optional<List<Aluno>> findBySerie(String serie);

    boolean existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(String nome, String dataNascimento, String nomeMae, String nomePai);
}
