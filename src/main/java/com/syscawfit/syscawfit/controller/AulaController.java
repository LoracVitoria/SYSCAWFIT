package com.syscawfit.syscawfit.controller;


import com.syscawfit.syscawfit.dao.AulaRepository;
import com.syscawfit.syscawfit.dao.UsuarioDao;
import com.syscawfit.syscawfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/aulas")
public class AulaController {


    @Autowired
    private AulaRepository aulaDao;

    @Autowired
    private UsuarioDao usuarioDao;


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
    public String salvarAula( Aula aula, Model model){
        aulaDao.save(aula);

        return "redirect:/aulas/list";
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
    public  String atualizarAula(Aula aula){
        aula = aulaDao.save(aula);

        return "redirect:/aulas/list";
    }

}
