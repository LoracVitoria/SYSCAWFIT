package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.UsuarioDao;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDao dao;

        //CONSULTAR USUARIO
        @RequestMapping("/list")
        public String buscar(Model model) {
            List<Usuario> listaUsuarios = new ArrayList<Usuario>();
            listaUsuarios.addAll(dao.findAll());
            model.addAllAttributes(listaUsuarios);
            return "/usuario/list.html";
        }
        // NOVO USUARIO
        @RequestMapping("/new")
        public String cadastrar(Model model){
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario );
            return "/usuario/usuario.html";
        }
        // REMOVE USUARIO
        @RequestMapping("/delete")
        public String deletar(Usuario usuario, Model model){
                dao.deleteById(usuario.getId());
            return "/usuario/list.html";
        }
        // ATUALIZA USUARIO
      @RequestMapping("/update")
        public String editar(Usuario usuario){
            return null;
        }

    }