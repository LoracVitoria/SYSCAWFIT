package com.syscawfit.dao;


import com.syscawfit.model.Aula;
import com.syscawfit.model.AulaDiaHora;
import com.syscawfit.model.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula,Long> {


    Aula findByAulaDiaHora(AulaDiaHora aulaDiaHora);
}

