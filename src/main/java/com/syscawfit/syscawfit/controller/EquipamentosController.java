package com.syscawfit.syscawfit.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.syscawfit.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.syscawfit.exceptions.DeleteEquipamentoException;
import com.syscawfit.syscawfit.model.Equipamentos;
import com.syscawfit.syscawfit.services.EquipamentosService;
import com.syscawfit.syscawfit.services.FileUploadUtil;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentosController {

	private static final String String = null;

	@Autowired
	private EquipamentosRepository daoEquipamentos;

	@Autowired
	private EquipamentosService equipamentosService;

	private String separator;

	private String caminhoImagens;

	@Autowired
	public EquipamentosController(EquipamentosRepository daoEquipamentos, EquipamentosService equipamentosService) {
		this.daoEquipamentos = daoEquipamentos;
		this.equipamentosService = equipamentosService;

		if (System.getProperty("os.name").contains("Windows")) {
			separator = "\\";
		} else {
			separator = "/";
		}

		caminhoImagens = "src" + separator + "main" + separator + "resources" + separator + "static" + separator + "img"
				+ separator + "equipamentosImagens" + separator;
	}

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
			@RequestParam("file") MultipartFile file) throws IOException {
		if (result.hasErrors()) {
			return "redirect:/equipamentos/cadastrarEquipamentos.html";
		}

		daoEquipamentos.save(equipamento);

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		equipamento.setNomeImagem(fileName);
		Equipamentos saveEquipamento = daoEquipamentos.save(equipamento);

		String uploadDir = caminhoImagens + saveEquipamento.getId();

		FileUploadUtil.saveFile(uploadDir, fileName, file);

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
	public String delete(Model model, @PathVariable Long id) throws IOException {

		Equipamentos equipamento = daoEquipamentos.findById(id).orElse(null);

		try {
			equipamentosService.deleteById(id);
			FileUtils.deleteDirectory(new File(caminhoImagens + equipamento.getId()));

		} catch (DeleteEquipamentoException e) {
			String msg = e.getMessage();
			List<String> mensagensErro = new ArrayList<String>();
			mensagensErro.add(msg);
			model.addAttribute("mensagensErro", mensagensErro);
			List<Equipamentos> equipamentosList = daoEquipamentos.findAll();
			model.addAttribute("equipamentosList", equipamentosList);
			return "/equipamentos/listarEquipamentos.html";
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

	@GetMapping("/get-image/{id}/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(Model model, @PathVariable("id") Long id, @PathVariable("imagem") String imagem)
			throws IOException {
		File imagemArquivo = new File(caminhoImagens + id + separator + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

}
