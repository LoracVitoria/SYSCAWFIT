package com.syscawfit.syscawfit.securityweb;

import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

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
        // Delete all
        this.userRepository.deleteAll();

        // Crete users
        Usuario carol = new Usuario("103.840.709-57", passwordEncoder.encode("carol123"), "USER", "");
        Usuario admin = new Usuario("070.923.780-44", passwordEncoder.encode("admin123"), "ADMIN", "ACCESS_TEST1,ACCESS_TEST2");
        Usuario manager = new Usuario("203.557.880-98", passwordEncoder.encode("manager123"), "MANAGER", "ACCESS_TEST1");

        List<Usuario> users = Arrays.asList(carol, admin, manager);

        // Save to db
        this.userRepository.saveAll(users);
    }
}