package com.syscawfit.syscawfit.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.syscawfit.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.syscawfit.exceptions.DeleteEquipamentoException;
import com.syscawfit.syscawfit.model.Equipamentos;
import com.syscawfit.syscawfit.services.EquipamentosService;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentosController {

	private static final String String = null;

	@Autowired
	private EquipamentosRepository daoEquipamentos;
	
	@Autowired
	private EquipamentosService equipamentosService;

	// caminho windows:
	public static String caminhoImagens = "..\\SYSCAWFIT\\src\\main\\resources\\static\\img\\equipamentosImagens\\";
	
//	caminho linuxe MacOs
//	public static String caminhoImagens = "../SYSCAWFIT/src/main/resources/static/img/equipamentosImagens/";
	
	// NOVO EQUIPAMENTO
	@RequestMapping("/new")
	public String newForm(Model model) {
		Equipamentos equipamentos = new Equipamentos();
		model.addAttribute("equipamentos", equipamentos);

		return "/equipamentos/cadastrarEquipamentos.html";
	}

	/* SAVE EQUIPAMENTOS */
	@PostMapping("/save")
	public String save(@Valid Equipamentos equipamento, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) {
		if (result.hasErrors()) {
			return "redirect:/equipamentos/cadastrarEquipamentos.html";
		}

		daoEquipamentos.save(equipamento);

		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(equipamento.getId()) + file.getOriginalFilename());
				Files.write(caminho, bytes);

				equipamento.setNomeImagem(String.valueOf(equipamento.getId()) + file.getOriginalFilename());
				daoEquipamentos.save(equipamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/equipamentos/list";
	}

	/* EDITAR EQUIPAMENTOS */
	@GetMapping("/update/{id}")
	public String getUpdate(Model model, @PathVariable("id") Long id) {
		Optional<Equipamentos> equipamentosOpt = daoEquipamentos.findById(id);
		if (equipamentosOpt.isEmpty()) {
			throw new IllegalArgumentException("equipamento não encontrado.");
		}
		model.addAttribute("equipamentos", equipamentosOpt.get());
		return "/equipamentos/cadastrarEquipamentos.html";
	}

	/* DELETE EQUIPAMENTOS */
	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		
		try{
		equipamentosService.deleteById(id);
		}catch (DeleteEquipamentoException e) {
			String msg = e.getMessage();
			model.addAttribute("mensagem", msg);
			System.out.println(msg);
		}
		return "redirect:/equipamentos/list";
	}

	// LISTA TODOS EQUIPAMENTOS
	@RequestMapping("/list")
	public String list(Model model) {
		List<Equipamentos> equipamentosList = daoEquipamentos.findAll();
		model.addAttribute("equipamentosList", equipamentosList);

		return "/equipamentos/listarEquipamentos.html";
	}

	@GetMapping("/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(Model model, @PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());

		}

		return null;
	}

}
