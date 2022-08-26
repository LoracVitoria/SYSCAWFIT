package com.syscawfit.syscawfit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syscawfit.syscawfit.dao.TipoExercicioRepository;
import com.syscawfit.syscawfit.exceptions.DeleteTipoExercicioException;

@Service
public class TipoExerciciosService {
	@Autowired
	private TipoExercicioRepository daoExercicio;
	
	public void deleteById(Long id) {
		try {
		daoExercicio.deleteById(id);
		} catch (Exception e) {
			throw new DeleteTipoExercicioException("Este tipo de exercício não pode ser "
					+ "excluído pois está vinculado a um ou mais treinos");
		}
	}
}
