package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.model.Aluno;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {


        //CONSULTAR USUARIO
        @RequestMapping("/list")
        public String buscar(Model model) {
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario );
            return "/usuario/list.html";
        }

        // NOVO USUARIO
        @RequestMapping("/new")
        public String cadastrar(Model model){
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario );
            return "/usuario/manter-usuario.html";
        }


    }
