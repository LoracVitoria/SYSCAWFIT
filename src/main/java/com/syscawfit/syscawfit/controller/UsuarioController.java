package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.UsuarioDao;
import com.syscawfit.syscawfit.model.Aluno;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {


    @Autowired
    private UsuarioDao usuarioDao;


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

        return "/usuario/usuario.html";
    }

    @PostMapping("/save")
    public String salvarUsuario(Usuario usuario, Model model){
        usuarioDao.save(usuario);

        return "redirect:/usuario/list";
    }




}