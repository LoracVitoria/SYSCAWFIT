package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Aluno;
import com.syscawfit.syscawfit.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
