package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno,Long> {

    //findAll, findById, save, delete, update
}
