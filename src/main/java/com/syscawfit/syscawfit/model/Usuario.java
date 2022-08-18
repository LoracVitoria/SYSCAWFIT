package com.syscawfit.syscawfit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Boolean mantenedor;
    private String senha;
     @Transient
    private String imagemUsuario;
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoUsuario endereco;
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipoFuncionario;
    public Usuario() {

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

    public Boolean getMantenedor() {
        return mantenedor;
    }

    public void setMantenedor(Boolean mantenedor) {
        this.mantenedor = mantenedor;
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
}