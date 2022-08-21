package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.EnderecoUsuarioRepository;
import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.Equipamentos;
import com.syscawfit.syscawfit.model.TipoFuncionario;
import com.syscawfit.syscawfit.model.TipoUsuario;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
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
            if (cpf.isBlank() || cpf.isEmpty() || cpf==null) {
                usuarios = daoUsuario.findAll();
            } else {
                Usuario usuario = daoUsuario.findByCpf(cpf);
                if (usuario != null) {
                    usuarios = List.of(usuario);
                } else {
                    return "redirect:/usuario/list.html";
                }
            }
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("usuario", new Usuario());

            return "/usuario/list.html";
        }
        // NOVO USUARIO
        @RequestMapping("/new")
        public String create(Model model){
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario);
            //configurar aqui
            model.addAttribute("tipoFuncionario", TipoFuncionario.values());
            model.addAttribute("tipoUsuario", TipoUsuario.values());

            return "usuario/cadastro.html";
        }
      @PostMapping("/save")
        public String save(Usuario usuario) {
            if (usuario.getEndereco() != null) {
                daoEndereco.save(usuario.getEndereco());
            }
          if(usuario.getTipoUsuario() != null && usuario.getTipoUsuario().name().compareTo("Mantenedor")==0){
              usuario.setRoles("ADMIN");
          }else if(usuario.getTipoUsuario() != null && usuario.getTipoUsuario().name().compareTo( "Funcionário")==0) {
              usuario.setRoles("USER");
          }
            daoUsuario.save(usuario);
            return "redirect:/usuario/list";
        }
        // REMOVE USUARIO
        @RequestMapping("/delete/{id}")
        public String delete(@PathVariable Long id){
                daoUsuario.deleteById(id);
            return "redirect:/usuario/list";
        }
        // ATUALIZA USUARIO
      @GetMapping("/update/{id}")
        public String update( Model model, @PathVariable("id") Long id){
            Optional<Usuario> usuarioOptional = daoUsuario.findById(id);
          model.addAttribute("usuario", usuarioOptional.get());
          if (usuarioOptional.isEmpty()) {
              throw new IllegalArgumentException("Usuário não encontrado!");
          }
          return "/usuario/cadastro.html";
        }

    }