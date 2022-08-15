package com.syscawfit.syscawfit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.model.Aluno;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoDao;
//
//	//CONSULTAR ALUNO
//	@RequestMapping("/list")
//	public String buscar(Model model) {
//		Aluno aluno = new Aluno();
//		model.addAttribute("aluno", aluno );
//		return "/aluno/list.html";
//	}
//
	// NOVO ALUNO
	@RequestMapping("/new")
	public String cadastrar(Model model){
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno);
		return "/aluno/aluno.html";
	}
	
	@PostMapping("/save")
	public String save(@Valid Aluno aluno, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "redirect:/aluno/aluno.html";
		}
		
		alunoDao.save(aluno);

		return "redirect:/aluno/new";
	}

	
	

}

