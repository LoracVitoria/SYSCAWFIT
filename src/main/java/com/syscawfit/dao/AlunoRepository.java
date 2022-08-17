package com.syscawfit.dao;

import com.syscawfit.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno,Long> {

    //findAll, findById, save, delete, update
    Aluno findByCpf(String cpf);


}
