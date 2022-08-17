package com.syscawfit.controller;

import com.syscawfit.dao.AlunoRepository;
import com.syscawfit.dao.PlanoRepository;
import com.syscawfit.model.Aluno;
import com.syscawfit.model.Plano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {


    @Autowired
    private AlunoRepository alunoDao;

    @Autowired
    private PlanoRepository planoDao;


    @RequestMapping("/filtro")
    public String gerarRelatorio(Model model){

        List<Aluno> alunoList = alunoDao.findAll();
        List<Plano> planoList = planoDao.findAll();

        model.addAttribute("alunoList", alunoList);
        model.addAttribute("planoList",planoList);

        return "/relatorios/relatorio";
    }


    public Integer buscarTotalAlunos(List<Aluno> alunos){

        // buscar todos os alunos da lista
        alunos = alunoDao.findAll();

        Integer total_alunos = alunos.size();

        return total_alunos;
    }



}