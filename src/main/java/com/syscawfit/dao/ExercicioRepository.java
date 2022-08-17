package com.syscawfit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syscawfit.model.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

}
