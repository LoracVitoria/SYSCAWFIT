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

	private Optional<Treino> treinoOpt;

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

	// listar treino por aluno
	@GetMapping("/listTreinos")
	public String listaTreinoAluno(Model model, String cpf) {
		listaTreinoPorAluno.clear();
		List<Treino> t1 = daoTreino.findAll();

		for (int i = 0; i < t1.size(); i++) {
			if (cpf == "") {
				return "redirect:/treino/listTreinos";
			} else if(cpf == null) {
				listaTreinoPorAluno = daoTreino.findAll();
				
			}
			else if (t1.get(i).getAluno().getCpf().equals(cpf)) {
				listaTreinoPorAluno.add(t1.get(i));
			}
		}

		model.addAttribute("listaAlunosCpf", listaTreinoPorAluno);

		return "/treino/listarTreinos.html";
	}

	@RequestMapping("/abrirTreino/{id}")
	public String verificarTreinos(Model model, @PathVariable Long id) {
		List<Treino> t1 = daoTreino.findAll();
		List<Exercicio> listaExercicio = new ArrayList<Exercicio>();
		List<Treino> treinoListAbrir = new ArrayList<Treino>();
		for (int i = 0; i < t1.size(); i++) {
			if (t1.get(i).getId().equals(id)) {
				treinoListAbrir.add(t1.get(i));
				listaExercicio = t1.get(i).getListaExercicios();
			}
		}

		model.addAttribute("treinoListAbrir", treinoListAbrir);
		model.addAttribute("listaExercicios", listaExercicio);
		return "/treino/abrirTreino.html";
	}

	/* EDITAR TREINO */
	@GetMapping("/update/{id}")
	public String getUpdate(Model model, @PathVariable("id") Long id) {
		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		List<Treino> t1 = daoTreino.findAll();
		treinoOpt = daoTreino.findById(id);
		if (treinoOpt.isEmpty()) {
			throw new IllegalArgumentException("Treino não encontrado.");
		}

		for (int i = 0; i < t1.size(); i++) {
			if (t1.get(i).getId().equals(id)) {
				exerciciosLista = t1.get(i).getListaExercicios();
				treino = treinoOpt.get();
			}

		}

		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);
		model.addAttribute("listaAlunos", listaAlunos);
		model.addAttribute("exerciciosLista", exerciciosLista);
		model.addAttribute("treino", treino);
		return "/treino/editarTreino.html";
	}

	/* UPDATE TREINO */
	@PostMapping("/updateTreino")
	public String update(Treino treino, Model model) {
		daoTreino.save(treino);
		return "redirect:/treino/list";
	}

	/* DELETE TREINO */
	@RequestMapping("/deleteTreino/{id}")
	public String delete(Model model, @PathVariable Long id) {
		for (Exercicio e : exerciciosLista) {
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
	@PostMapping("/exercicioSaveEditar")
	public String saveExercicioEditar(@Valid Exercicio exercicio, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/treino/EditarTreino.html";
		}
		
		exerciciosLista.add(exercicio);
		
		return "redirect:/treino/exercicioListEditarTreino";
	}

//	 LISTAR TODOS OS EXERCICIOS
	@RequestMapping("/exercicioList")
	public String listExercicios(Model model) {

		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		Treino t1 = new Treino();
		model.addAttribute("treino", t1);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);
		model.addAttribute("exerciciosLista", exerciciosLista);
		model.addAttribute("listaAlunos", listaAlunos);
		System.out.println(exerciciosLista);
		return "/treino/cadastrarTreino.html";
	}
	
//	 LISTAR TODOS OS EXERCICIOS
	@RequestMapping("/exercicioListEditarTreino")
	public String listaDeExercicios(Model model) {

		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		model.addAttribute("treino", treino);
		model.addAttribute("exercicio", exercicio);
		model.addAttribute("tipoExercicioLista", tipoExercicioLista);
		model.addAttribute("exerciciosLista", exerciciosLista);
		model.addAttribute("listaAlunos", listaAlunos);
		System.out.println(exerciciosLista);
		return "/treino/editarTreino.html";
	}

	/* DELETE EXERCICIO */
	@RequestMapping("/deleteExercicio/{exercicio}")
	public String deleteExercicio(Model model, @PathVariable String exercicio) {

		for (int i = 0; i < exerciciosLista.size(); i++) {
			if (exercicio.equals(exerciciosLista.get(i).toString())) {
				exerciciosLista.remove(i);
			}
		}

		return "redirect:/treino/exercicioList";
	}

	/* DELETE EXERCICIO EDITAR TREINO */
	@RequestMapping("/deleteExercicioEditar/{id}")
	public String deleteExercicio(Model model, @PathVariable Long id) {

		for (int i = 0; i < exerciciosLista.size(); i++) {
			if (exerciciosLista.get(i).getId().equals(id)) {
				daoExercicio.deleteById(id);
				exerciciosLista.remove(i);
			}
		}

		return "redirect:/treino/exercicioListEditarTreino";
	}

}