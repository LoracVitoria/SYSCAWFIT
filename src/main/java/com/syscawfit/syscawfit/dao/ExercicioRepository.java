package com.syscawfit.syscawfit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syscawfit.syscawfit.model.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

	public Exercicio getByTreinoId(Long id);
}
