package com.syscawfit.syscawfit.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo Nome é obrigatório!")
    @Size(min=2,max=50,message="Nome deve conter {min} até {max} caracteres.")
    @Column(nullable = false)
    private String nome;

//    @Column(nullable = false)
//    @NotBlank(message="O campo Senha é obrigatório!")
//    private String senha;
//
//    @Column(nullable = false)
//    @NotBlank(message = "O campo CPF é obrigatório!")
//     private String  cpf;
//
//    @Column(nullable = false)
//    @NotBlank(message = "O campo RG é obrigatório!")
//    private String rg;
//
//    @Column(nullable = false)
//    @NotBlank(message = "O campo telefone é obrigatório!")
//    private String telefone;
//
//    @Column(nullable = false, unique=true)
//    @NotBlank(message="O campo Email é obrigatório!")
//    @Email(message = "Email inválido!")
//    private String email;
//
//    @ManyToOne
//    @JoinColumn(name = "endereco_ID")
//    private Endereco endereco;
//
//    @Column(nullable = false)
//    @NotBlank(message = "Selecione uma opção!")
//    private Boolean situacao;
//
//    private Boolean tipoUsuario;
    //onde true para administrador e false para funcionário

//    public Endereco getEndereco() {
//        return endereco;
//    }
//    public void setEndereco(Endereco endereco) {
//        this.endereco = endereco;
//    }

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



}