package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.dao.PlanoRepository;
import com.syscawfit.syscawfit.model.Aluno;
import com.syscawfit.syscawfit.model.Plano;
import com.syscawfit.syscawfit.model.TipoPlano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
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
        int totalAlunosMensal = 0;
        int totalAlunosTrimestral = 0;
        int totalAlunosSemestral = 0;
        int totalAlunosAnual = 0;
        float valorPlanosTipoMensal = 0;
        float valorPlanosTipoTrimestral = 0;
        float valorPlanosTipoSemestral = 0;
        float valorPlanosTipoAnual = 0;
        float valorTotalPlanos = 0;

        // Total de alunos
        Integer total_alunos = alunoList.size();

        // Valor total por plano
        for (Aluno aluno : alunoList) {
            if(aluno.getPlano() == TipoPlano.Mensal) {
                totalAlunosMensal += 1;
            }

            if(aluno.getPlano() == TipoPlano.Trimestral){
                totalAlunosTrimestral += 1;
            }

            if(aluno.getPlano() == TipoPlano.Semestral){
                totalAlunosSemestral += 1;
            }

            if(aluno.getPlano() == TipoPlano.Anual){
                totalAlunosAnual += 1;
            }
        }

        for (Plano plano : planoList) {
            if (plano.getTipo() == TipoPlano.Mensal) {
                valorPlanosTipoMensal = totalAlunosMensal * plano.getValor();
            }
                if (plano.getTipo() == TipoPlano.Trimestral) {
                valorPlanosTipoTrimestral = totalAlunosTrimestral * plano.getValor();
            }
            if (plano.getTipo() == TipoPlano.Semestral) {
                valorPlanosTipoSemestral = totalAlunosSemestral * plano.getValor();
            }
            if (plano.getTipo() == TipoPlano.Anual) {
                valorPlanosTipoAnual = totalAlunosAnual * plano.getValor();
            }
        }

        valorTotalPlanos = valorPlanosTipoMensal + valorPlanosTipoTrimestral + valorPlanosTipoSemestral + valorPlanosTipoAnual;

        model.addAttribute("total_alunos", total_alunos);
        model.addAttribute("totalAlunosMensal",totalAlunosMensal);
        model.addAttribute("totalAlunosTrimestral",totalAlunosTrimestral);
        model.addAttribute("totalAlunosSemestral",totalAlunosSemestral);
        model.addAttribute("totalAlunosAnual",totalAlunosAnual);
        model.addAttribute("valorPlanosTipoMensal",valorPlanosTipoMensal);
        model.addAttribute("valorPlanosTipoTrimestral",valorPlanosTipoTrimestral);
        model.addAttribute("valorPlanosTipoSemestral",valorPlanosTipoSemestral);
        model.addAttribute("valorPlanosTipoAnual",valorPlanosTipoAnual);
        model.addAttribute("valorTotalPlanos",valorTotalPlanos);


        return "/relatorios/relatorio";
    }










}
