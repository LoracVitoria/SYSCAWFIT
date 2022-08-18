package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.EnderecoUsuarioRepository;
import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.TipoFuncionario;
import com.syscawfit.syscawfit.model.TipoUsuario;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository daoUsuario;
    @Autowired
    private EnderecoUsuarioRepository daoEndereco;

        //CONSULTAR USUARIO
        @RequestMapping("/list")
        public String list(Model model, String cpf) {
            List<Usuario> usuarios = new ArrayList<>();
            if (cpf.equals("")) {
                return "redirect:/usuario/list";
            } else if (cpf == null) {
                usuarios = daoUsuario.findAll();
            } else {
                Usuario usuario = daoUsuario.findByCpf(cpf);

                if (usuario != null) {
                    usuarios = List.of(usuario);
                } else {
                    return "redirect:/usuario/list.html";
                }
            }
            model.addAttribute("usuarios", usuarios );
            model.addAttribute("usuario", new Usuario());

            return "/usuario/list.html";
        }
        // NOVO USUARIO
        @RequestMapping("/new")
        public String create(Model model){
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario );
            //configurar aqui
            model.addAttribute("tipoFuncionario", TipoFuncionario.values());
            model.addAttribute("tipoUsuario", TipoUsuario.values());

            return "usuario/usuario.html";
        }
      @PostMapping("/save")
        public String saveUsuario(Usuario usuario) {
            daoEndereco.save(usuario.getEndereco());
            daoUsuario.save(usuario);
            return "redirect:/usuario/list.html";
        }
        // REMOVE USUARIO
        @RequestMapping("/delete")
        public String delete(Usuario usuario){
                daoEndereco.deleteById(usuario.getEndereco().getId());
                daoUsuario.deleteById(usuario.getId());
            return "redirect:/usuario/list.html";
        }
        // ATUALIZA USUARIO
      @PostMapping("/update")
        public String update( Model model, @PathVariable Long id){
          Usuario usuario = daoUsuario.findById(id).orElse(null);
          model.addAttribute("usuario", usuario);
          return "/usuario/list.html";
        }

    }