package com.syscawfit.syscawfit.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
//import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Treino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicioTreino;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate datafimTreino;
	
//	@ManyToMany
//	private Usuario responsavelTreino;
//	pegar o usuario logado (springSecurity)
	
	@OneToMany(mappedBy = "treino")
	private List<Exercicio> listaExercicios;
	
	public Treino() {}
	
	public Treino(String nome, LocalDate dataInicioTreino, LocalDate datafimTreino, List<Exercicio> listaExercicios) {
		super();
		this.nome = nome;
		this.dataInicioTreino = dataInicioTreino;
		this.datafimTreino = datafimTreino;
		this.listaExercicios = listaExercicios;
	}


	public LocalDate getDataInicioTreino() {
		return dataInicioTreino;
	}

	public void setDataInicioTreino(LocalDate dataInicioTreino) {
		this.dataInicioTreino = dataInicioTreino;
	}

	public LocalDate getDatafimTreino() {
		return datafimTreino;
	}

	public void setDatafimTreino(LocalDate datafimTreino) {
		this.datafimTreino = datafimTreino;
	}

	public List<Exercicio> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<Exercicio> listaExercicios) {
		this.listaExercicios = listaExercicios;
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

	@Override
	public String toString() {
		return "Treino [id=" + id + ", nome=" + nome + ", dataInicioTreino=" + dataInicioTreino + ", datafimTreino="
				+ datafimTreino + "]";
	}
	//inserir lista de exercicios no toString();

	
}
