package com.syscawfit.dao;

import com.syscawfit.model.Aluno;
import com.syscawfit.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
