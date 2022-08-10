package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario,Long> {
    @Override
    void deleteById(Long aLong);

    @Override
    Optional<Usuario> findById(Long aLong);

    @Override
    Usuario getById(Long aLong);

    @Override
    <S extends Usuario> S save(S entity);




    //findAll, findById, get, save, delete, update
}


