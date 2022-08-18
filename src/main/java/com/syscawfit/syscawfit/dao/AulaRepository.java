package com.syscawfit.syscawfit.dao;


import com.syscawfit.syscawfit.model.Aula;
import com.syscawfit.syscawfit.model.AulaDiaHora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula,Long> {


    Aula findByAulaDiaHora(AulaDiaHora aulaDiaHora);
}

