package com.syscawfit.syscawfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.syscawfit.syscawfit.model.Aluno;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	//CONSULTAR ALUNO
	@RequestMapping("/list")
	public String buscar(Model model) {
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno );
		return "/aluno/list.html";
	}

	// NOVO ALUNO
	@RequestMapping("/new")
	public String cadastrar(Model model){
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno);
		return "/aluno/aluno.html";
	}

}

