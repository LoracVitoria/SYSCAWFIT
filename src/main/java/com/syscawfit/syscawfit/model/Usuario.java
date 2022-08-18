package com.syscawfit.syscawfit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private String email;
    private Boolean situacao;
    private String senha;
//    private String imagemUsuario;

    @NotNull
    @OneToOne(cascade = CascadeType.REMOVE)
    private EnderecoUsuario endereco;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipoFuncionario;

    public Usuario() {}


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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", situacao=" + situacao +
                ", senha='" + senha + '\'' +
//                ", imagemUsuario='" + imagemUsuario + '\'' +
                ", endereco=" + endereco +
                ", tipoUsuario=" + tipoUsuario +
                ", tipoFuncionario=" + tipoFuncionario +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome)
                && Objects.equals(cpf, usuario.cpf) && Objects.equals(rg, usuario.rg)
                && Objects.equals(telefone, usuario.telefone) && Objects.equals(email, usuario.email)
                && Objects.equals(situacao, usuario.situacao)
                && Objects.equals(senha, usuario.senha) && Objects.equals(endereco, usuario.endereco)
                && tipoUsuario == usuario.tipoUsuario && tipoFuncionario == usuario.tipoFuncionario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, rg, telefone, email, situacao, senha, endereco, tipoUsuario, tipoFuncionario);
    }
}