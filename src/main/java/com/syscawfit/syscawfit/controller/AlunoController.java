package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.dao.EnderecoRepository;
import com.syscawfit.syscawfit.model.Endereco;
import com.syscawfit.syscawfit.model.TipoPlano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.syscawfit.syscawfit.model.Aluno;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoDao;

	@Autowired
	private EnderecoRepository enderecoDao;

	// Retorna Página de Consulta, com lista de Todos os Alunos
	@RequestMapping("/list")
	public String listarAlunos(Model model, String cpf) {
		
		List<Aluno> alunos = new ArrayList<Aluno>();

		if (cpf == "") {
			return "redirect:/aluno/list";
		} else if (cpf == null) {
			alunos = alunoDao.findAll();
		} else {
			alunos = List.of(alunoDao.findByCpf(cpf));
		}
		
		model.addAttribute("alunos", alunos );
		model.addAttribute("aluno", new Aluno());

		return "/aluno/list.html";
	}

	// Retorna Página de Cadastro de Aluno
	@RequestMapping("/new")
	public String formularioAluno(Model model){
		Aluno aluno = new Aluno();
		Endereco endereco = new Endereco();
		model.addAttribute("aluno", aluno);
		model.addAttribute("endereco", endereco);
		model.addAttribute("planos", TipoPlano.values());

		return "/aluno/aluno.html";
	}

	// Salvar Aluno
	@PostMapping("/save")
	public String salvarAluno(Aluno aluno,Endereco endereco, Model model){
		alunoDao.save(aluno);
		endereco.setAluno(aluno);
		enderecoDao.save(endereco);

		return "redirect:/aluno/list";
	}

	// Deletar Aluno
	@RequestMapping("/delete/{id}")
	public String deletarAluno(Model model, @PathVariable Long id){
		// Buscar aluno por ID no Banco de Dados
		Aluno aluno = alunoDao.findById(id).orElse(null);

		// Buscar lista de endereços de Aluno
		List<Endereco> enderecos = enderecoDao.findByAluno(aluno);

		// Deleta todos os endereços da lista de Endereços
		enderecos.forEach(endereco -> {
			enderecoDao.delete(endereco);
		});

		// Deleta Aluno do Banco
		alunoDao.delete(aluno);

		return "redirect:/aluno/list";
	}

	// Editar aluno
	@RequestMapping("/editar/{id}")
	public String editarAluno(Model model, @PathVariable Long id){
		Aluno aluno = alunoDao.findById(id).orElse(null);
		
		// Procura primeiro endereço da lista de endereços por Aluno
		Endereco endereco = enderecoDao.findByAluno(aluno).get(0);

		model.addAttribute("aluno", aluno);
		model.addAttribute("endereco", endereco);
		model.addAttribute("planos", TipoPlano.values());

		return "/aluno/editar.html";
	}

	@PostMapping("/update")
	public  String atualizarAluno(Aluno aluno, Endereco endereco, Model model){
		aluno.setId(alunoDao.findByCpf(aluno.getCpf()).getId());
		endereco.setId(enderecoDao.findByAluno(aluno).get(0).getId());

		endereco.setAluno(aluno);

		alunoDao.save(aluno);
		enderecoDao.save(endereco);

		return "redirect:/aluno/list";
	}

	// Buscar aluno por ID


}

