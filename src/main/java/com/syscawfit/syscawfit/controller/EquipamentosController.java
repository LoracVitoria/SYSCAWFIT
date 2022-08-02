package com.syscawfit.syscawfit.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syscawfit.syscawfit.dao.EquipamentosRepository;
import com.syscawfit.syscawfit.model.Equipamentos;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentosController {

	@Autowired
	private EquipamentosRepository daoEquipamentos;

	private static String caminhoImagens = "C:\\Users\\willi\\Pictures\\img";

	// NOVO EQUIPAMENTO
	@RequestMapping("/new")
	public String newForm(Model model) {
		Equipamentos equipamentos = new Equipamentos();
		model.addAttribute("equipamentos", equipamentos);

		return "/equipamentos/cadastrarEquipamentos.html";
	}

	/* SAVE EQUIPAMENTOS */
	@PostMapping("/save")
	public String save(@Valid Equipamentos equipamento, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "redirect:/equipamentos/cadastrarEquipamentos.html";
		}

		daoEquipamentos.save(equipamento);
//				
//				try {
//					if(!arquivo.isEmpty()) {
//						byte[] bytes = arquivo.getBytes();
//						Path caminho = Paths.get(caminhoImagens+String.valueOf(equipamento.getId())+arquivo.getOriginalFilename());
//						Files.write(caminho, bytes);
//						equipamento.setNomeImagem(String.valueOf(equipamento.getId())+arquivo.getOriginalFilename());
//					}
//				}catch(IOException e) {
//					e.printStackTrace();
//				}

		return "redirect:/equipamentos/list";
	}

	/* UPDATE EQUIPAMENTOS */
	@PostMapping("/update")
	public String update(Equipamentos equipamento, Model model) {
		daoEquipamentos.save(equipamento);
		return "redirect:/equipamentos/list";
	}
	

	@RequestMapping("/update/{id}")
	public String getUpdate(Model model, @PathVariable Long id) {
		Optional<Equipamentos> equipamento = daoEquipamentos.findById(id);
				
		model.addAttribute("equipamentos", equipamento);

		return "redirect:/equipamentos/cadastrarEquipamentos.html";
	}

	/* DELETE EQUIPAMENTOS */
	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		daoEquipamentos.deleteById(id);
		return "redirect:/equipamentos/list";
	}

	// LISTA TODOS EQUIPAMENTOS
	@RequestMapping("/list")
	public String list(Model model) {
		List<Equipamentos> equipamentosList = daoEquipamentos.findAll();
		model.addAttribute("equipamentosList", equipamentosList);

		return "/equipamentos/listarEquipamentos.html";
	}

}
