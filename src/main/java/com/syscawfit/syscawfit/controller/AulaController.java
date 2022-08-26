package com.syscawfit.syscawfit.controller;


import com.syscawfit.syscawfit.dao.AulaRepository;
import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/aulas")
public class AulaController {


    @Autowired
    private AulaRepository aulaDao;

    @Autowired
    private UsuarioRepository usuarioDao;


    // Retorna página com grade de aulas
    @RequestMapping("/list")
    public String buscarAulas(Model model){

       List<Aula> aulas = aulaDao.findAll();

       model.addAttribute("aulas",aulas);

        return "/aulas/grade";
    }

    // Retorna página de cadastro de aula
    @RequestMapping("/new")
    public String novaAula(Model model){
        Aula aula = new Aula();
        AulaDiaHora aulaDiaHora = new AulaDiaHora();
        List<Usuario> professores = usuarioDao.findAll();

        model.addAttribute("aula",aula);
        model.addAttribute("diasSemana",DiaSemana.values());
        model.addAttribute("professores",professores);

        return "/aulas/aula";
    }


    // Salvar aula
    @PostMapping("/save")
    public String salvarAula( @Valid Aula aula, BindingResult result, Model model){

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }

        if (aulaDao.findByAulaDiaHora(aula.getAulaDiaHora()) != null) {
            errors.add("Já existe aula cadastrada neste dia e horário!");
        }

        List<Usuario> professores = usuarioDao.findAll();

        if (!errors.isEmpty()) {
            model.addAttribute("aula",aula);
            model.addAttribute("diasSemana",DiaSemana.values());
            model.addAttribute("professores", professores);
            model.addAttribute("mensagensErro", errors);

            return "/aulas/aula";
        }

        try {
            aulaDao.save(aula);
            return "redirect:/aulas/list";

        } catch (ConstraintViolationException e) {
            return "redirect:/aulas/list";
        }
    }

    // Deletar aula
    @RequestMapping("/delete/{id}")
    public String deletarAula(Model model, @PathVariable Long id){
        Aula aula = aulaDao.findById(id).orElse(null);
        aulaDao.delete(aula);

        return "redirect:/aulas/list";
    }

    // Atualizar aula
    @RequestMapping("/editar/{id}")
    public String editarAula(Model model, @PathVariable Long id){
        Aula aula = aulaDao.findById(id).orElse(null);
        List<Usuario> professores = usuarioDao.findAll();

        model.addAttribute("aula",aula);
        model.addAttribute("diasSemana",DiaSemana.values());
        model.addAttribute("professores",professores);

        return "/aulas/editarAula";
    }

    @PostMapping("/update")
    public  String atualizarAula(@Valid Aula aula,BindingResult result, Model model){

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }

        if (aulaDao.findByAulaDiaHora(aula.getAulaDiaHora()) != null) {
            errors.add("Já existe aula cadastrada neste dia e horário!");
        }

        List<Usuario> professores = usuarioDao.findAll();

        if (!errors.isEmpty()) {
            model.addAttribute("aula",aula);
            model.addAttribute("diasSemana",DiaSemana.values());
            model.addAttribute("professores", professores);
            model.addAttribute("mensagensErro", errors);

            return "/aulas/aula";
        }

        try {
            aulaDao.save(aula);
            return "redirect:/aulas/list";

        } catch (ConstraintViolationException e) {
            return "redirect:/aulas/list";
        }
    }
}
