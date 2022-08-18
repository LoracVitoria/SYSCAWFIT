package com.syscawfit.syscawfit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.syscawfit.dao.TipoExercicioRepository;
import com.syscawfit.syscawfit.exceptions.DeleteTipoExercicioException;
import com.syscawfit.syscawfit.model.Equipamentos;
import com.syscawfit.syscawfit.model.TipoExercicio;
import com.syscawfit.syscawfit.services.TipoExerciciosService;

@Controller
@RequestMapping("/tipoexercicio")
public class TipoExercicioController {

	@Autowired
	private TipoExercicioRepository daoExercicio;

	@Autowired
	private EquipamentosRepository daoEquipamento;

	@Autowired
	private TipoExerciciosService tipoExerciciosService;

	private Optional<TipoExercicio> t1;

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
	@GetMapping("/update/{id}")
	public String getUpdate(Model model, @PathVariable("id") Long id) {
		t1 = daoExercicio.findById(id);
		List<Equipamentos> equipamentosList = daoEquipamento.findAll();
		if (t1.isEmpty()) {
			throw new IllegalArgumentException("equipamento não encontrado.");
		}

		model.addAttribute("equipamentosList", equipamentosList);
		model.addAttribute("tipoExercicio", t1.get());

		return "/exercicios/cadastrarTipoExercicio.html";
	}

	/* DELETE TIPO_EXERCICIO */
	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		try {
			tipoExerciciosService.deleteById(id);
		} catch (DeleteTipoExercicioException e) {
			String msg = e.getMessage();
			List<TipoExercicio> tipoExercicioLista = daoExercicio.findAll();
			List<String> mensagensErro = new ArrayList<String>();
			mensagensErro.add(msg);
			model.addAttribute("mensagensErro", mensagensErro);
			model.addAttribute("tipoExercicioLista", tipoExercicioLista);
//			System.out.println(msg);
			return "/exercicios/listaTipoExercicio.html";
		}

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
