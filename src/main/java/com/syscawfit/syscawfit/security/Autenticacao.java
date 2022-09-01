package com.syscawfit.syscawfit.security;

import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.syscawfit.syscawfit.model.TipoFuncionario.Administrativo;
import static com.syscawfit.syscawfit.model.TipoUsuario.Mantenedor;

@Service
public class Autenticacao implements CommandLineRunner {
    private UsuarioRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public Autenticacao(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Usuario manager = new Usuario("103.840.709-57", passwordEncoder.encode("123123"),"Carol", "MANAGER",true, Mantenedor,Administrativo, "imagem-usuario.jpg");

        if(this.userRepository.findByCpf(manager.getCpf()) == null){
            this.userRepository.save(manager);
        }

    }
}