package com.syscawfit.syscawfit.model;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
//import java.util.Date;
//import java.util.List;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    
    private String cpf;

	public Aluno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aluno(Long id, String nome, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
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

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}
    
    
	
    
    
    
//    private String imagem_aluno;
//    private String telefone;
//    private String email;
//
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    private Date dataNascimento;
//
//    @Enumerated(value = EnumType.STRING)
//    private TipoPlano plano;
//
//     @OneToMany
//     List<Endereco> endereco;

}
