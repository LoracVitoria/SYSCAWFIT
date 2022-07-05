package com.syscawfit.syscawfit.dao;

import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario,Long> {

    //findAll, findById, get, save, delete, update
}


