package com.syscawfit.syscawfit.controller;


import com.syscawfit.syscawfit.dao.PlanoRepository;
import com.syscawfit.syscawfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoRepository planoDao;


    // Retorna página de planos
    @RequestMapping()
    public String novoPlano(Model model){

        Plano plano = new Plano();

        model.addAttribute("plano",plano);
        model.addAttribute("planos", TipoPlano.values());

        return "/planos/plano";
    }

    // Salvar plano
    @PostMapping("/save")
    public String salvarAula(Plano plano, Model model){
        planoDao.save(plano);

        return "redirect:/planos";
    }


}
