package com.syscawfit.syscawfit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Exercicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String nome;

	private Integer qtdeRepeticoes;

	private Integer qtdeSeries;

	private Integer carga;
	
	@ManyToOne
	@JoinColumn(name = "tipoExercicio_id")
	private TipoExercicio tipoExercicio;
	
	@ManyToOne
	@JoinColumn(name = "treino_id")
	private Treino treino;
	
	public Exercicio() {
		super();
	}

	public Exercicio(String nome, TipoExercicio tipoExercicio, Integer qtdeRepeticoes, Integer qtdeSeries,
			Integer carga, Treino treino) {
		super();
		this.nome = nome;
		this.tipoExercicio = tipoExercicio;
		this.qtdeRepeticoes = qtdeRepeticoes;
		this.qtdeSeries = qtdeSeries;
		this.carga = carga;
		this.treino = treino;
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

	public TipoExercicio getTipoExercicio() {
		return tipoExercicio;
	}

	public void setTipoExercicio(TipoExercicio tipoExercicio) {
		this.tipoExercicio = tipoExercicio;
	}

	public Integer getQtdeRepeticoes() {
		return qtdeRepeticoes;
	}

	public void setQtdeRepeticoes(Integer qtdeRepeticoes) {
		this.qtdeRepeticoes = qtdeRepeticoes;
	}

	public Integer getQtdeSeries() {
		return qtdeSeries;
	}

	public void setQtdeSeries(Integer qtdeSeries) {
		this.qtdeSeries = qtdeSeries;
	}

	public Integer getCarga() {
		return carga;
	}

	public void setCarga(Integer carga) {
		this.carga = carga;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}

	@Override
	public String toString() {
		return "Exercicio [id=" + id + ", nome=" + nome + ", qtdeRepeticoes=" + qtdeRepeticoes + ", qtdeSeries="
				+ qtdeSeries + ", carga=" + carga + ", tipoExercicio=" + tipoExercicio + ", treino=" + treino + "]";
	}
	


}
