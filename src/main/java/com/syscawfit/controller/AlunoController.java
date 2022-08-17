package com.syscawfit.controller;

import com.syscawfit.dao.AlunoRepository;
import com.syscawfit.dao.EnderecoRepository;
import com.syscawfit.model.Endereco;
import com.syscawfit.model.TipoPlano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.syscawfit.model.Aluno;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoDao;

	@Autowired
	private EnderecoRepository enderecoDao;

	// Retorna Página de Consulta, com lista de Todos os Alunos ou Aluno por CPF
	@RequestMapping("/list")
	public String listarAlunos(Model model, String cpf) {
		
		List<Aluno> alunos = new ArrayList<Aluno>();

		if (cpf == "") {
			return "redirect:/aluno/list";
		} else if (cpf == null) {
			alunos = alunoDao.findAll();
		} else {
			Aluno aluno = alunoDao.findByCpf(cpf);

			if (aluno != null) {
				alunos = List.of(aluno);
			} else {
				return "redirect:/aluno/list";
			}

		}
		
		model.addAttribute("alunos", alunos );
		model.addAttribute("aluno", new Aluno());

		return "/aluno/list.html";
	}

	// Retorna Página de Cadastro de Aluno
	@RequestMapping("/new")
	public String formularioAluno(Model model){
		Aluno aluno = new Aluno();
		model.addAttribute("aluno", aluno);
		model.addAttribute("planos", TipoPlano.values());

		return "aluno/aluno.html";
	}

	// Salvar Aluno
	@PostMapping("/save")
	public String salvarAluno(Aluno aluno, Model model){
		enderecoDao.save(aluno.getEndereco());
		alunoDao.save(aluno);

		return "redirect:/aluno/list";
	}

	// Deletar Aluno
	@RequestMapping("/delete/{id}")
	public String deletarAluno(Model model, @PathVariable Long id){
		// Buscar aluno por ID no Banco de Dados
		Aluno aluno = alunoDao.findById(id).orElse(null);

		// Deleta Aluno do Banco
		alunoDao.delete(aluno);

		return "redirect:/aluno/list";
	}

	// Editar aluno
	@RequestMapping("/editar/{id}")
	public String editarAluno(Model model, @PathVariable Long id){
		Aluno aluno = alunoDao.findById(id).orElse(null);

		model.addAttribute("aluno", aluno);
		model.addAttribute("planos", TipoPlano.values());

		return "/aluno/editar.html";
	}

	@PostMapping("/update")
	public  String atualizarAluno(Aluno aluno, Model model){
		aluno.setId(alunoDao.findByCpf(aluno.getCpf()).getId());

		enderecoDao.save(aluno.getEndereco());
		alunoDao.save(aluno);

		return "redirect:/aluno/list";
	}

}

