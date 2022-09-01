package com.syscawfit.syscawfit.security;


import com.syscawfit.syscawfit.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {
    private Usuario user;
    private String imagemUsuarioLogado;
    private String nomeUsuarioLogado;
    private Long idUsuarioLogado;


    public UsuarioPrincipal(Usuario user) {
        this.user = user;
        this.imagemUsuarioLogado = this.user.getImagemUsuario();
        this.nomeUsuarioLogado = this.user.getNome();
        this.idUsuarioLogado = this.user.getId();
    }

    public String getImagemUsuarioLogado() {
        return imagemUsuarioLogado;
    }

    public String getNomeUsuarioLogado() {
        return nomeUsuarioLogado;
    }

    public Long getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();


        // Extract list of roles (ROLE_name)
        this.user.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getSenha();
    }
    @Override
    public String getUsername() {
        return this.user.getCpf();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getSituacao();
    }

}

