package com.syscawfit.syscawfit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syscawfit.syscawfit.model.TipoExercicio;

public interface TipoExercicioRepository extends JpaRepository<TipoExercicio, Long>{

	 //findAll, findById, get, save, delete, update
}
