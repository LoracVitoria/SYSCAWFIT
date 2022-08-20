package com.syscawfit.syscawfit.securityweb;

import com.syscawfit.syscawfit.dao.UsuarioRepository;
import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        UsuarioPrincipal userPrincipal = new UsuarioPrincipal(user);

        return userPrincipal;
    }

}
