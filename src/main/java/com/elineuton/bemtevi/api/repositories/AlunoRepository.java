package com.elineuton.bemtevi.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elineuton.bemtevi.api.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
