package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Plano;
import com.syscawfit.syscawfit.model.TipoPlano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano,Long> {

    Optional<Plano> findPlanoByTipo(TipoPlano tipoPlano);
}
