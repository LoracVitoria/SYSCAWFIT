package com.syscawfit.syscawfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.syscawfit.syscawfit.model.Aluno;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	//Página aluno
	@GetMapping
	public String page () {

		return "aluno.html";
	}

	//NOVO EQUIPAMENTO
	@RequestMapping("/new")
	public String newForm(Model model) {
		//objeto filme será mapeado ao ${equipamentos} na view(equipamentos.html)
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno );


		return "/aluno/aluno.html";
	}

}

