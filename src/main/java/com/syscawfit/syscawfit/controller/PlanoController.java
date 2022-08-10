package com.syscawfit.syscawfit.controller;


import com.syscawfit.syscawfit.dao.PlanoRepository;
import com.syscawfit.syscawfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoRepository planoDao;

    // Retorna página de planos
    @RequestMapping()
    public String novoPlano(Model model){

        Plano plano = new Plano();
        List<Plano> planos = planoDao.findAll();

        model.addAttribute("plano",plano);
        model.addAttribute("planos",planos);
        model.addAttribute("tiposPlanos", TipoPlano.values());

        return "/planos/plano";
    }

    // Salvar plano
    @PostMapping("/save")
    public String salvarAula(Plano plano, Model model){
        //buscar no banco plano por tipo, caso não seja encontrado retornar nulo
        Plano planoExistente = planoDao.findPlanoByTipo(plano.getTipo()).orElse(null);

        // verificar se busca encontrou plano, se encontrou setar para plano o ID do planoExistente encontrado
        if (planoExistente != null) {
            plano.setId(planoExistente.getId());
        }

        // salvar caso planoExistente seja null
        planoDao.save(plano);

        return "redirect:/planos";
    }


}
