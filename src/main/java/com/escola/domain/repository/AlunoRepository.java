package com.escola.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escola.domain.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
