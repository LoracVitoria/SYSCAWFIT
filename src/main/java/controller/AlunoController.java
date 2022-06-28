package controller;

import dao.AlunoDao;
import model.Aluno;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/aluno")
public class AlunoController {

    private AlunoDao alunoDao;


    @GetMapping
    public String aluno (Model model) {
        System.out.println("Test");
        return "aluno.html";
    }


}

