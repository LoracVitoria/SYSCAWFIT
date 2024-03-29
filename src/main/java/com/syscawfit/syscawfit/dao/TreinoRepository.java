package com.syscawfit.syscawfit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syscawfit.syscawfit.model.Treino;


public interface TreinoRepository extends JpaRepository<Treino, Long>{

	public List<Treino> findTreinosByAlunoCpf(String cpf);
}
