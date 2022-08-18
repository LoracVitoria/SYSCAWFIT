package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Override
    void deleteById(Long aLong);

    @Override
    Optional<Usuario> findById(Long aLong);

    @Override
    Usuario getById(Long aLong);


    Usuario findByCpf(String username);


    //findAll, findById, get, save, delete,
}


