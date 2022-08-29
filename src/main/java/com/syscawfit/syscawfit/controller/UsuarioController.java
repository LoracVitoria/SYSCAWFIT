package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.AlunoRepository;
import com.syscawfit.syscawfit.dao.EnderecoAlunoRepository;
import com.syscawfit.syscawfit.dao.EnderecoUsuarioRepository;
import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.*;
import com.syscawfit.syscawfit.security.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository daoUsuario;
    @Autowired
    private EnderecoUsuarioRepository daoEndereco;
    private String separator;
    private String caminhoImagens;

    public UsuarioController(UsuarioRepository daoUsuario, EnderecoUsuarioRepository daoEndereco) {
        this.daoUsuario = daoUsuario;
        this.daoEndereco = daoEndereco;

        if (System.getProperty("os.name").contains("Windows")) {
            separator = "\\";
        } else {
            separator = "/";
        }

        caminhoImagens = "src" + separator + "main" + separator + "resources" + separator + "static" + separator +
                "img" + separator + "alunosImagens" + separator;

    }

    //CONSULTAR USUARIO
    @GetMapping("/list")
    public String list(Model model, String cpf) {
        List<Usuario> usuarios = new ArrayList<>();
        if (cpf == "") {
            return "redirect:/admin/usuario/list";
        } else if (cpf == null) {
            usuarios = daoUsuario.findAll();
        } else {
            Usuario usuario = daoUsuario.findByCpf(cpf);
            if (usuario != null) {
                usuarios = List.of(usuario);
            } else {
                return "redirect:/admin/usuario/list";
            }
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuario", new Usuario());

        return "/usuario/list.html";
    }

    // NOVO USUARIO
    @RequestMapping("/new")
    public String create(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        //configurar aqui
        model.addAttribute("tipoFuncionario", TipoFuncionario.values());
        model.addAttribute("tipoUsuario", TipoUsuario.values());

        return "usuario/cadastro.html";
    }

    @PostMapping("/save")
    public String save(@Valid Usuario usuario, BindingResult result, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }

        if (daoUsuario.findByCpf(usuario.getCpf()) != null) {
            errors.add("CPF já cadastrado!");
        }
        if (usuario.getEndereco() != null) {
            daoEndereco.save(usuario.getEndereco());
        }

        if (!errors.isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("mensagensErro", errors);

            return "/usuario/cadastro.html";
        }

        //obter nome do arquivo que será armazenado no BD no campo ImagemAluno
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        usuario.setImagemUsuario(fileName);

        // armazenar arquivo no diretório alunos-imagens/alunoID
        String uploadDir = caminhoImagens + usuario.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Mantenedor") == 0) {
            usuario.setRoles("ADMIN");
        } else if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Funcionário") == 0) {
            usuario.setRoles("USER");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCripto = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripto);

        daoUsuario.save(usuario);
        return "redirect:/admin/usuario/list";
    }

    // REMOVE USUARIO
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws IllegalAccessException {
        if (daoUsuario.findById(id).get().getRoles().compareTo("MANAGER") != 0) {
            daoUsuario.deleteById(id);
        } else {
            throw new IllegalAccessException("Este usuário não pode ser excluído!");
        }

        return "redirect:/admin/usuario/list";
    }

    // ATUALIZA USUARIO
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = daoUsuario.findById(id);
        model.addAttribute("usuario", usuarioOptional.get());
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        return "/usuario/cadastro.html";
    }

}