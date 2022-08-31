package com.syscawfit.syscawfit.controller;


import com.syscawfit.syscawfit.dao.PlanoRepository;
import com.syscawfit.syscawfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin/planos")
public class PlanoController {

    @Autowired
    private PlanoRepository planoDao;

    // Retorna página de planos
    @RequestMapping()
    public String novoPlano(Model model){

        Plano plano = new Plano();
        List<Plano> planos = planoDao.findAll();
        Plano planoMensal = new Plano();
        Plano planoTrimestral = new Plano();
        Plano planoSemestral = new Plano();
        Plano planoAnual = new Plano();

        for (Plano plano1 : planos) {
            if (plano1.getTipo() == TipoPlano.Mensal) {
                planoMensal = plano1;
            } else if (plano1.getTipo() == TipoPlano.Trimestral) {
                planoTrimestral = plano1;
            } else if (plano1.getTipo() == TipoPlano.Semestral) {
                planoSemestral = plano1;
            } else if (plano1.getTipo() == TipoPlano.Anual) {
                planoAnual = plano1;
            }
        };

        model.addAttribute("plano",plano);
        model.addAttribute("planoMensal", planoMensal);
        model.addAttribute("planoTrimestral", planoTrimestral);
        model.addAttribute("planoSemestral", planoSemestral);
        model.addAttribute("planoAnual", planoAnual);
        model.addAttribute("tiposPlanos", TipoPlano.values());

        return "/planos/plano";
    }

    // Salvar plano
    @PostMapping("/save")
    public String salvarAula(@Valid Plano plano, BindingResult result, Model model){

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });

            List<Plano> planos = planoDao.findAll();

            model.addAttribute("plano",plano);
            model.addAttribute("planos",planos);
            model.addAttribute("tiposPlanos", TipoPlano.values());
            model.addAttribute("mensagensErro", errors);

            return "/planos/plano";
        }

        //buscar no banco plano por tipo, caso não seja encontrado retornar nulo
        Plano planoExistente = planoDao.findPlanoByTipo(plano.getTipo()).orElse(null);

        // verificar se busca encontrou plano, se encontrou setar para plano o ID do planoExistente encontrado
        if (planoExistente != null) {
            plano.setId(planoExistente.getId());
        }

        // salvar caso planoExistente seja null
        planoDao.save(plano);

        return "redirect:/admin/planos";
    }

    // Deletar plano
    @RequestMapping("/delete/{id}")
    public String deletarPlano(Model model, @PathVariable Long id){
        Plano plano = planoDao.findById(id).orElse(null);
        planoDao.delete(plano);

        return "redirect:/planos";
    }


}
