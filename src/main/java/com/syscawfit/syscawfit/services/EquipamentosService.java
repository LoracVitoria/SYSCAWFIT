package com.syscawfit.syscawfit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syscawfit.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.syscawfit.exceptions.DeleteEquipamentoException;


@Service
public class EquipamentosService {
	
	@Autowired
	private EquipamentosRepository daoEquipamentos;
	
	public void deleteById(Long id) {
		
		try {
			daoEquipamentos.deleteById(id);
		
		} catch (Exception e) {
			throw new DeleteEquipamentoException(
					"Este equipamento não pode ser excluído pois esta vinculado a um ou mais Tipos de Exercicios");
		}
		
	}

}
