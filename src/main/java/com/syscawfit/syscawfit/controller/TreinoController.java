package com.syscawfit.syscawfit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.syscawfit.dao.TreinoRepository;
import com.syscawfit.syscawfit.model.Equipamentos;
import com.syscawfit.syscawfit.model.Treino;


@Controller
@RequestMapping("/treino")
public class TreinoController {
	
	@Autowired
	private TreinoRepository daoTreino;
	
	// NOVO TREINO
		@RequestMapping("/new")
		public String newForm(Model model) {
			Treino treino = new Treino();
			model.addAttribute("treino", treino);

			return "/treino/cadastrarTreino.html";
		}
		
		/* SAVE TREINO */
		@PostMapping("/save")
		public String save(@Valid Treino treino, BindingResult result, Model model) {
			if (result.hasErrors()) {
				return "redirect:/treino/cadastrarTreino.html";
			}

			daoTreino.save(treino);


			return "redirect:/treino/list";
		}
		
		// LISTA TODOS OS TREINOS
		@RequestMapping("/list")
		public String list(Model model) {
			List<Treino> treinoLista = daoTreino.findAll();
			model.addAttribute("treinoLista", treinoLista);

			return "/treino/listarTreinos.html";
		}
		
		/* UPDATE TREINO */
		@PostMapping("/update")
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



}
