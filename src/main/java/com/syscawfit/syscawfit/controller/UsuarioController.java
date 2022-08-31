package com.syscawfit.syscawfit.controller;

import com.syscawfit.syscawfit.dao.EnderecoUsuarioRepository;
import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.*;
import com.syscawfit.syscawfit.security.UsuarioPrincipal;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
                "img" + separator + "usuariosImagens" + separator;

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
        model.addAttribute("tipoFuncionario", TipoFuncionario.values());
        model.addAttribute("tipoUsuario", TipoUsuario.values());

        return "usuario/cadastro.html";
    }

    @PostMapping("/save")
    public String salvarAluno(@Valid Usuario usuario, BindingResult result, Model model,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }

        if (daoUsuario.findByCpf(usuario.getCpf()) != null) {
            errors.add("CPF já cadastrado!");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("planos", TipoUsuario.values());
            model.addAttribute("generos", TipoFuncionario.values());
            model.addAttribute("mensagemErro", errors);

            return "/usuario/cadastro.html";
        }
        if (usuario.getEndereco() != null) {
            daoEndereco.save(usuario.getEndereco());
        }
        if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Mantenedor") == 0) {
            usuario.setRoles("ADMIN");
        } else if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Funcionário") == 0) {
            usuario.setRoles("USER");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCripto = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripto);
        daoUsuario.save(usuario);

        //obter nome do arquivo que será armazenado no BD no campo ImagemAluno
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        usuario.setImagemUsuario(fileName);
        Usuario saveUsuario = daoUsuario.save(usuario);

        // armazenar arquivo no diretório alunos-imagens/alunoID

        String uploadDir = caminhoImagens + saveUsuario.getId();

        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        return "redirect:/admin/usuario/list";

    }

    // REMOVE USUARIO
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) throws IllegalAccessException {
        Optional<Usuario> usuario = daoUsuario.findById(id);
        if (usuario.isPresent() && usuario.get().getRoles().compareTo("MANAGER") != 0) {
            daoUsuario.deleteById(id);
        } else {
            model.addAttribute("mensagemErro","Este usuário não pode ser excluído!");
            return "/admin/usuario/list";
        }
        return "redirect:/admin/usuario/list";
    }

    // ATUALIZA USUARIO
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = daoUsuario.findById(id);
        model.addAttribute("usuario", usuarioOptional.get());
        model.addAttribute("tipoFuncionario", TipoFuncionario.values());
        model.addAttribute("tipoUsuario", TipoUsuario.values());
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        return "/usuario/edicao.html";
    }

    @PostMapping("/update")
    public String update(@Valid Usuario usuario, BindingResult result, Model model,
                         @RequestParam("image") MultipartFile multipartFile) throws IOException {

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }

        Usuario usuarioFind = daoUsuario.findByCpf(usuario.getCpf());


        if (!errors.isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("tipoUsuario", TipoUsuario.values());
            model.addAttribute("tipoFuncionario", TipoFuncionario.values());
            model.addAttribute("mensagemErro", errors);

            return "/usuario/edicao.html";
        }

        usuario.setId(daoUsuario.findByCpf(usuario.getCpf()).getId());

        //obter nome do arquivo que será armazenado no BD no campo ImagemAluno
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // Checar se foto foi alterada
        if (fileName.isEmpty()) {
            usuario.setImagemUsuario(usuarioFind.getImagemUsuario());
        } else {
            // Deleta imagem anterior
            FileUtils.deleteDirectory(new File(caminhoImagens + usuario.getId()));

            usuario.setImagemUsuario(fileName);

            // armazenar arquivo no diretório
            String uploadDir = caminhoImagens + usuario.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        if (usuario.getEndereco() != null) {
            daoEndereco.save(usuario.getEndereco());
        }
        if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Mantenedor") == 0) {
            usuario.setRoles("ADMIN");
        } else if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getTipoUsuario().compareTo("Funcionário") == 0) {
            usuario.setRoles("USER");
        }
        if(!usuario.getSenha().isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCripto = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCripto);
        }
        //configurar melhor isso aqui
        if (usuario.getRoles().compareTo("MANAGER") == 0) {
            model.addAttribute("mensagemErro","Este usuário não pode ter o seu tipo de usuário alterado!");
            return "/admin/usuario/list";
        }
        daoUsuario.save(usuario);

        return "redirect:/admin/usuario/list";
    }

    @RequestMapping("/view/{id}")
    public String visualizarDados(Model model, @PathVariable Long id) {
        Usuario usuario = daoUsuario.findById(id).orElse(null);

        model.addAttribute("usuario", usuario);
        model.addAttribute("tipoUsuario", TipoUsuario.values());
        model.addAttribute("tipoFuncionario", TipoFuncionario.values());

        return "/usuario/visualizacao";
    }

    @GetMapping("/get-image/{id}/{imagem}")
    @ResponseBody
    public byte[] retornarImagem(Model model, @PathVariable("id") Long id, @PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(caminhoImagens + id + separator + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }

}