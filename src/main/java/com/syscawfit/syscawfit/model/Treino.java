package com.syscawfit.syscawfit.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Treino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String nome;
	
	private LocalDate dataInicioTreino;
	
	private LocalDate dataFimTreino;
	
//	private Usuario responsavelTreino;
	
	@OneToMany
	private List<Exercicio> listaExercicios;

	
	public Treino() {
		super();
	}

	public Treino( String nome,	LocalDate dataInicioTreino, LocalDate dataFimTreino, List<Exercicio> listaExercicios) {
	super();
	this.nome = nome;
	this.dataInicioTreino = dataInicioTreino;
	this.dataFimTreino = dataFimTreino;
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

	public LocalDate getDataInicioTreino() {
		return dataInicioTreino;
	}

	public void setDataInicioTreino(LocalDate dataInicioTreino) {
		this.dataInicioTreino = dataInicioTreino;
	}

	public LocalDate getDataFimTreino() {
		return dataFimTreino;
	}

	public void setDataFimTreino(LocalDate dataFimTreino) {
		this.dataFimTreino = dataFimTreino;
	}

	public List<Exercicio> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<Exercicio> listaExercicios) {
		this.listaExercicios = listaExercicios;
	}

	@Override
	public String toString() {
		return "Treino [id=" + id + ", nome=" + nome + ", dataInicioTreino=" + dataInicioTreino + ", dataFimTreino="
				+ dataFimTreino + ", listaExercicios=" + listaExercicios + "]";
	}
	

	
}
