package com.syscawfit.syscawfit.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    @Column(unique = true)
    private String cpf;
    private String rg;
    private String telefone;
    @Size(max=1000)
    private String imagemUsuario;
    private String email;
    @NotNull
    private Boolean situacao;
    @NotNull
    private String senha;
//    private String imagemUsuario;
    @Valid
    @OneToOne(cascade = CascadeType.REMOVE)
    private EnderecoUsuario endereco;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipoFuncionario;
    private String roles = "";

    private String tokenRedefinirSenha;

    public Usuario() {
    }

    public Usuario(String username, String password, String nome, String roles, Boolean situacao, TipoUsuario tipoUsuario, TipoFuncionario tipoFuncionario) {
        this.cpf = username;
        this.senha = password;
        this.nome = nome;
        this.situacao = situacao;
        this.tipoUsuario = tipoUsuario;
        this.tipoFuncionario = tipoFuncionario;
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public EnderecoUsuario getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoUsuario endereco) {
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public String getTokenRedefinirSenha() {
        return tokenRedefinirSenha;
    }

    public void setTokenRedefinirSenha(String tokenRedefinirSenha) {
        this.tokenRedefinirSenha = tokenRedefinirSenha;
    }

    public String getImagemUsuario() {
        return imagemUsuario;
    }
    public void setImagemUsuario(String fileName) {
        this.imagemUsuario = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(cpf, usuario.cpf) && Objects.equals(rg, usuario.rg) && Objects.equals(telefone, usuario.telefone) && Objects.equals(imagemUsuario, usuario.imagemUsuario) && Objects.equals(email, usuario.email) && Objects.equals(situacao, usuario.situacao) && Objects.equals(senha, usuario.senha) && Objects.equals(endereco, usuario.endereco) && tipoUsuario == usuario.tipoUsuario && tipoFuncionario == usuario.tipoFuncionario && Objects.equals(roles, usuario.roles) && Objects.equals(tokenRedefinirSenha, usuario.tokenRedefinirSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, rg, telefone, imagemUsuario, email, situacao, senha, endereco, tipoUsuario, tipoFuncionario, roles, tokenRedefinirSenha);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", telefone='" + telefone + '\'' +
                ", imagemUsuario='" + imagemUsuario + '\'' +
                ", email='" + email + '\'' +
                ", situacao=" + situacao +
                ", senha='" + senha + '\'' +
                ", endereco=" + endereco +
                ", tipoUsuario=" + tipoUsuario +
                ", tipoFuncionario=" + tipoFuncionario +
                ", roles='" + roles + '\'' +
                ", tokenRedefinirSenha='" + tokenRedefinirSenha + '\'' +
                '}';
    }

}