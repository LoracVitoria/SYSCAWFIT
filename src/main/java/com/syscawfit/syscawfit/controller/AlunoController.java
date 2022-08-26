package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.dao.EnderecoAlunoRepository;
import com.syscawfit.syscawfit.model.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/aluno")
public class AlunoController {

	private AlunoRepository alunoDao;

	private EnderecoAlunoRepository enderecoDao;

	private String separator;

	private String caminhoImagens;

	@Autowired
	public AlunoController(AlunoRepository alunoDao, EnderecoAlunoRepository enderecoDao) {
		this.alunoDao = alunoDao;
		this.enderecoDao = enderecoDao;

		if (System.getProperty("os.name").contains("Windows")) {
			separator = "\\";
		} else {
			separator = "/";
		}

		caminhoImagens = "src" + separator + "main" + separator + "resources" + separator + "static" + separator +
						 "img" + separator + "alunosImagens" + separator;
	}

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
		model.addAttribute("generos", Genero.values());

		return "/aluno/aluno.html";
	}

	// Salvar Aluno
	@PostMapping("/save")
	public String salvarAluno(@Valid Aluno aluno, BindingResult result, Model model,
							  @RequestParam("image") MultipartFile multipartFile) throws IOException {

		List<String> errors = new ArrayList<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> {
				errors.add(error.getDefaultMessage());
			});
		}

		if (alunoDao.findByCpf(aluno.getCpf()) != null) {
			errors.add("CPF já cadastrado!");
		}

		if (!errors.isEmpty()) {
			model.addAttribute("aluno", aluno);
			model.addAttribute("planos", TipoPlano.values());
			model.addAttribute("generos", Genero.values());
			model.addAttribute("mensagensErro", errors);

			return "/aluno/aluno.html";
		}

		enderecoDao.save(aluno.getEndereco());
		alunoDao.save(aluno);

		//obter nome do arquivo que será armazenado no BD no campo ImagemAluno
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		aluno.setImagemAluno(fileName);
		Aluno saveAluno = alunoDao.save(aluno);

		// armazenar arquivo no diretório alunos-imagens/alunoID

		String uploadDir = caminhoImagens + saveAluno.getId();

		FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

		return "redirect:/aluno/list";

	}

	// Deletar Aluno
	@RequestMapping("/delete/{id}")
	public String deletarAluno(Model model, @PathVariable Long id) throws IOException {
		// Buscar aluno por ID no Banco de Dados
		Aluno aluno = alunoDao.findById(id).orElse(null);

		// Deleta Aluno do Banco
		alunoDao.delete(aluno);

		// Deleta diretório de imagem
		FileUtils.deleteDirectory(new File(caminhoImagens + aluno.getId()));

		return "redirect:/aluno/list";
	}

	// Editar aluno
	@RequestMapping("/editar/{id}")
	public String editarAluno(Model model, @PathVariable Long id){
		Aluno aluno = alunoDao.findById(id).orElse(null);

		model.addAttribute("aluno", aluno);
		model.addAttribute("planos", TipoPlano.values());
		model.addAttribute("generos", Genero.values());

		return "/aluno/editar.html";
	}

	@PostMapping("/update")
	public  String atualizarAluno(@Valid Aluno aluno, BindingResult result, Model model,
								  @RequestParam("image") MultipartFile multipartFile) throws IOException {

		List<String> errors = new ArrayList<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> {
				errors.add(error.getDefaultMessage());
			});
		}

		Aluno buscaAluno = alunoDao.findByCpf(aluno.getCpf());

		if (buscaAluno.getId() != aluno.getId() && buscaAluno != null) {
			errors.add("CPF já cadastrado!");
		}

		if (!errors.isEmpty()) {
			model.addAttribute("aluno", aluno);
			model.addAttribute("planos", TipoPlano.values());
			model.addAttribute("generos", Genero.values());
			model.addAttribute("mensagensErro", errors);

			return "/aluno/editar.html";
		}

		aluno.setId(alunoDao.findByCpf(aluno.getCpf()).getId());

		//obter nome do arquivo que será armazenado no BD no campo ImagemAluno
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		// Checar se foto foi alterada
		if (fileName.isEmpty()) {
			aluno.setImagemAluno(buscaAluno.getImagemAluno());
		} else {
			// Deleta imagem anterior
			FileUtils.deleteDirectory(new File(caminhoImagens + aluno.getId()));

			aluno.setImagemAluno(fileName);

			// armazenar arquivo no diretório
			String uploadDir = caminhoImagens + aluno.getId();

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}

		enderecoDao.save(aluno.getEndereco());
		alunoDao.save(aluno);

		return "redirect:/aluno/list";
	}


	// Mostrar dados aluno
	@RequestMapping("/visualizar/{id}")
	public String visualizarDadosAluno(Model model, @PathVariable Long id){
		Aluno aluno = alunoDao.findById(id).orElse(null);

		model.addAttribute("aluno", aluno);
		model.addAttribute("planos", TipoPlano.values());
		model.addAttribute("generos", Genero.values());

		return "/aluno/visualizar";
	}

	@GetMapping("/get-image/{id}/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(Model model, @PathVariable("id") Long id, @PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + id + separator + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

}

