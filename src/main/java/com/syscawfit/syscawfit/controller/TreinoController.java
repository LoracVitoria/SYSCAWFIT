package com.syscawfit.syscawfit.controller;

import java.time.LocalDate;
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
		List<String> mensagensErro = new ArrayList<String>();
		Exercicio exercicio = new Exercicio();
		List<TipoExercicio> tipoExercicioLista = daoTipoExercicio.findAll();
		List<Aluno> listaAlunos = daoAluno.findAll();
		
		if (result.hasErrors()) {
			String msg2 = "verifique os campos!";
			mensagensErro.add(msg2);
			model.addAttribute("mensagensErro", mensagensErro);
			model.addAttribute("treino", treino);
			model.addAttribute("exerciciosLista", exerciciosLista);
			model.addAttribute("exercicio", exercicio);
			model.addAttribute("tipoExercicioLista", tipoExercicioLista);
			model.addAttribute("listaAlunos", listaAlunos);
			return "/treino/cadastrarTreino.html";
		}
		
		if(treino.getDatafimTreino().getDayOfYear() < treino.getDataInicioTreino().getDayOfYear())  {
			String msg = "A data final do treino não pode ser menor que a data de início!";
			mensagensErro.add(msg);
			model.addAttribute("mensagensErro", mensagensErro);
			model.addAttribute("treino", treino);
			model.addAttribute("exerciciosLista", exerciciosLista);
			model.addAttribute("exercicio", exercicio);
			model.addAttribute("tipoExercicioLista", tipoExercicioLista);
			model.addAttribute("listaAlunos", listaAlunos);
			return "/treino/cadastrarTreino.html";
		}
		
		if(exerciciosLista.isEmpty()) {
			String msg = "O treino não pode ser cadastrado sem pelo menos um exercicio listado!";
			mensagensErro.add(msg);
			model.addAttribute("mensagensErro", mensagensErro);
			model.addAttribute("treino", treino);
			model.addAttribute("exerciciosLista", exerciciosLista);
			model.addAttribute("exercicio", exercicio);
			model.addAttribute("tipoExercicioLista", tipoExercicioLista);
			model.addAttribute("listaAlunos", listaAlunos);
			return "/treino/cadastrarTreino.html";
		}
		
		daoTreino.save(treino);

		for (Exercicio e : exerciciosLista) {
			e.setTreino(treino);
			daoExercicio.save(e);
		}

		treino.setListaExercicios(exerciciosLista);

		daoTreino.save(treino);


		return "redirect:/treino/listTreinos";
	}

	// listar treino por aluno
	@GetMapping("/listTreinos")
	public String listaTreinoAluno(Model model, String cpf) {
		List<Treino> t1 = new ArrayList<Treino>();
		
			if (cpf == "") {
				return "redirect:/treino/listTreinos";
			} else if(cpf == null) {
				t1 = daoTreino.findAll();
			}
			else {
				t1 = daoTreino.findTreinosByAlunoCpf(cpf);
				if(t1.isEmpty()) {
					String msg = "Não existem treinos cadastrados para este CPF!";
					model.addAttribute("mensagensErro", msg);
					t1 = daoTreino.findAll(); 
				}
			}
		

		model.addAttribute("listaAlunosCpf", t1);

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

	/* DELETE TREINO */
	@RequestMapping("/deleteTreino/{id}")
	public String delete(Model model, @PathVariable Long id) {

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
			return "redirect:/treino/editarTreino.html";
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
		model.addAttribute("listaAlunos", listaAlunos);
		model.addAttribute("exerciciosLista", exerciciosLista);
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
		model.addAttribute("listaAlunos", listaAlunos);
		model.addAttribute("exerciciosLista", exerciciosLista);
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