package com.syscawfit.syscawfit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.dao.ExercicioRepository;
import com.syscawfit.syscawfit.dao.TipoExercicioRepository;
import com.syscawfit.syscawfit.dao.TreinoRepository;
import com.syscawfit.syscawfit.model.Aluno;
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
	
	@Autowired
	private AlunoRepository daoAluno;

	private Treino treino = new Treino();

	private List<Exercicio> exerciciosLista = new ArrayList<Exercicio>();
	
	List<Treino> listaTreinoPorAluno = new ArrayList<Treino>();

	// NOVO TREINO
	@RequestMapping("/new")
	public String newForm(Model model) {
		exerciciosLista.clear();
		Treino treino = new Treino();
		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		model.addAttribute("treino", treino);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);
		model.addAttribute("listaAlunos", listaAlunos);
		

		return "/treino/cadastrarTreino.html";
	}

	/* SAVE TREINO */
	@PostMapping("/saveTreino")
	public String save(@Valid Treino treino, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "redirect:/treino/cadastrarTreino.html";
		}

		daoTreino.save(treino);

		for (Exercicio e : exerciciosLista) {
			e.setTreino(treino);
			daoExercicio.save(e);
		}

		treino.setListaExercicios(exerciciosLista);

		daoTreino.save(treino);

		exerciciosLista.clear();

		return "redirect:/treino/listTreinos";
	}
	
	//listar treino por aluno
	@GetMapping("/listTreinos")
	public String listaTreinoAluno(Model model, String cpf) {
		listaTreinoPorAluno.clear();
		List<Treino> treinoLista = daoTreino.findAll();
		
		for(int i=0; i<treinoLista.size(); i++) {
			if(cpf == "") {
				
				return "redirect:/treino/listTreinos";
			}
			else if(treinoLista.get(i).getAluno().getCpf().equals(cpf)) {
				
				listaTreinoPorAluno.add(treinoLista.get(i));
				
				
			}
		}
		
		model.addAttribute("listaAlunosCpf", listaTreinoPorAluno);
		
		
		return "/treino/listarTreinos.html";
	}

//	// LISTA TODOS OS TREINOS
//	@RequestMapping("/listTreinos")
//	public String list(Model model) {
//		List<Treino> treinoLista = daoTreino.findAll();
//		model.addAttribute("treinoLista", treinoLista);
//		model.addAttribute("listaExercicios", treino.getListaExercicios());
//		return "/treino/listarTreinos.html";
//	}

	/* UPDATE TREINO */
	@PostMapping("/updateTreino")
	public String update(Treino treino, Model model) {
		daoTreino.save(treino);
		return "redirect:/treino/list";
	}

	/* DELETE TREINO */
	@RequestMapping("/deleteTreino/{id}")
	public String delete(Model model, @PathVariable Long id) {
		for(Exercicio e : exerciciosLista) {
			daoExercicio.deleteById(e.getId());
			exerciciosLista.remove(e);
		}
		daoTreino.deleteById(id);
		return "redirect:/treino/listTreinos";
	}

	/****************************************************************************************************************************/

	/* EXERCICIOS */

	/* SALVAR EXERCICIO */
	@PostMapping("/exercicioSave")
	public String saveExercicio(@Valid Exercicio exercicio, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/treino/cadastrarTreino.html";
		}


		exerciciosLista.add(exercicio);

		return "redirect:/treino/exercicioList";
	}

//	 LISTAR TODOS OS EXERCICIOS
	@RequestMapping("/exercicioList")
	public String listExercicios(Model model) {

		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		model.addAttribute("treino", treino);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);
		model.addAttribute("exerciciosLista", exerciciosLista);
		model.addAttribute("listaAlunos", listaAlunos);
		return "/treino/cadastrarTreino.html";
	}

	/* DELETE EXERCICIO */
	@RequestMapping("/deleteExercicio/{exercicio}")
	public String deleteExercicio(Model model, @PathVariable String exercicio) {

			
		for (int i = 0; i < exerciciosLista.size(); i++) {
			if (exercicio.equals(exerciciosLista.get(i).toString())) {
				exerciciosLista.remove(i);
//				daoExercicio.deleteById(id);
			}
		}

		return "redirect:/treino/exercicioList";
	}

}