package com.syscawfit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.dao.TipoExercicioRepository;
import com.syscawfit.model.Equipamentos;
import com.syscawfit.model.TipoExercicio;

@Controller
@RequestMapping("/tipoexercicio")
public class TipoExercicioController {

	@Autowired
	private TipoExercicioRepository daoExercicio;

	@Autowired
	private EquipamentosRepository daoEquipamento;

	/* NOVO TIPO_EXERCICIO */
	@RequestMapping("/new")
	public String form(Model model) {
		TipoExercicio tipoExercicio = new TipoExercicio();
		List<Equipamentos> equipamentosList = daoEquipamento.findAll();
		model.addAttribute("tipoExercicio", tipoExercicio);
		model.addAttribute("equipamentosList", equipamentosList);

		return "/exercicios/cadastrarTipoExercicio.html";
	}

	/* SALVAR TIPO_EXERCICIO */
	@PostMapping("/save")
	public String save(@Valid TipoExercicio tipoExercicio, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/exercicios/cadastrarTipoExercicio.html";
		}

		daoExercicio.save(tipoExercicio);

		return "redirect:/tipoexercicio/list";
	}

	/* UPDATE TIPO_EXERCICIO */
	@PostMapping("/update")
	public String update(TipoExercicio tipoExercicio, Model model) {
		daoExercicio.save(tipoExercicio);
		return "redirect:/tipoexercicio/list";
	}

	// ENVIAR DADOS DA BUSCA POR ID PARA A PAGINA cadastrarTipoExercicio.html
	@RequestMapping("/update/{id}")
	public String getUpdate(Model model, @PathVariable Long id) {

		model.addAttribute("tipoExercicio", daoExercicio.getById(id));

		return "redirect:/exercicios/cadastrarTipoExercicio.html";
	}

	/* DELETE TIPO_EXERCICIO */
	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		daoExercicio.deleteById(id);
		return "redirect:/tipoexercicio/list";
	}

	/* LISTA TODOS EQUIPAMENTOS */
	@RequestMapping("/list")
	public String list(Model model) {
		List<TipoExercicio> tipoExercicioLista = daoExercicio.findAll();
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);

		return "/exercicios/listaTipoExercicio.html";
	}

	
	
}
