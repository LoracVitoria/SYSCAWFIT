package com.syscawfit.syscawfit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syscawfit.syscawfit.dao.ExercicioRepository;
import com.syscawfit.syscawfit.dao.TipoExercicioRepository;
import com.syscawfit.syscawfit.dao.TreinoRepository;
import com.syscawfit.syscawfit.model.Exercicio;
import com.syscawfit.syscawfit.model.TipoExercicio;
import com.syscawfit.syscawfit.model.Treino;

@Controller
@RequestMapping("/treino")
public class TreinoController {

	@Autowired
	private TreinoRepository daoTreino;

	@Autowired
	private ExercicioRepository daoExercicio;

	@Autowired
	private TipoExercicioRepository daoTipoExercicio;
	
	private Treino treino = new Treino();
	
	private List<Exercicio> exerciciosLista = new ArrayList<Exercicio>();

	// NOVO TREINO
	@RequestMapping("/new")
	public String newForm(Model model) {
		Treino treino = new Treino();
		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		model.addAttribute("treino", treino);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);

		return "/treino/cadastrarTreino.html";
	}

	/* SAVE TREINO */
	@PostMapping("/saveTreino")
	public String save(@Valid Treino treino, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/treino/cadastrarTreino.html";
		}

		daoTreino.save(treino);

		return "redirect:/treino/list";
	}

	// LISTA TODOS OS TREINOS
	@RequestMapping("/listTreinos")
	public String list(Model model) {
		List<Treino> treinoLista = daoTreino.findAll();
		model.addAttribute("treinoLista", treinoLista);

		return "/treino/listarTreinos.html";
	}

	/* UPDATE TREINO */
	@PostMapping("/updateTreino")
	public String update(Treino treino, Model model) {
		daoTreino.save(treino);
		return "redirect:/treino/list";
	}

	/* DELETE TREINO */
	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		daoTreino.deleteById(id);
		return "redirect:/treino/list";
	}

	/****************************************************************************************************************************/

	/* EXERCICIOS */

	/* SALVAR EXERCICIO */
	@PostMapping("/exercicioSave")
	public String saveExercicio(@Valid Exercicio exercicio, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/treino/cadastrarTreino.html";
		}

		daoExercicio.save(exercicio);
		
		exerciciosLista.add(exercicio);
		
		return "redirect:/treino/exercicioList";
	}

//	 LISTAR TODOS OS EXERCICIOS
	@RequestMapping("/exercicioList")
	public String listExercicios(Model model) {
		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		model.addAttribute("treino", treino);
		model.addAttribute("exerciciosLista", exerciciosLista);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);

		return "/treino/cadastrarTreino.html";
	}
	
	/* DELETE EXERCICIO */
	@RequestMapping("/deleteExercicio/{id}")
	public String deleteExercicio(Model model, @PathVariable Long id) {
		daoExercicio.deleteById(id);
//		exerciciosLista.
		return "redirect:/treino/exercicioList";
	}


}
