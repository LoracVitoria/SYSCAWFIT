package com.syscawfit.syscawfit.services;

import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.exceptions.UsuarioNotFoundException;
import com.syscawfit.syscawfit.model.Usuario;
import com.syscawfit.syscawfit.security.UsuarioPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioPrincipalDetailsService implements UserDetailsService {
    private UsuarioRepository userRepository;

    public UsuarioPrincipalDetailsService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario user = this.userRepository.findByCpf(s);

        if(user == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        UsuarioPrincipal userPrincipal = new UsuarioPrincipal(user);

        return userPrincipal;
    }

    public Usuario findByCpf(String userCpf) {
        Usuario user = userRepository.findByCpf(userCpf);
        return user;
    }
    public void atualizarTokenSenhaRedefinida(String token, String email ) throws UsuarioNotFoundException {
        Usuario usuario = userRepository.findByEmail(email);
        if (usuario != null) {
            usuario.setTokenRedefinirSenha(token);
            userRepository.save(usuario);
        }
        else{
            throw new UsuarioNotFoundException("Não foi possível encontrar o e-mail informado!");
        }
    }
        public Usuario getUsuarioRedefinido(String tokenRedefinirSenha){
            return userRepository.findByTokenRedefinirSenha(tokenRedefinirSenha);
        }
    public void atualizarSenha(Usuario user, String novaSenha){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCripto = passwordEncoder.encode(novaSenha);
        user.setSenha(senhaCripto);
        user.setTokenRedefinirSenha(null);
        userRepository.save(user);
    }



}
