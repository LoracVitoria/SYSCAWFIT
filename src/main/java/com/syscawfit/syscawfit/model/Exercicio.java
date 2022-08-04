package com.syscawfit.syscawfit.model;

import javax.persistence.*;
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

	@ManyToOne
	@JoinColumn(name = "tipo_exercicio_id")
	private TipoExercicio tipoExercicio;
	
	private Integer qtdeRepeticoes;
	
	private Integer qtdeSeries;
	
	private Integer cargaKG;
	
	

	
	public Exercicio() {
		super();
	}
	
	

	public Exercicio( String nome, TipoExercicio tipoExercicio, Integer qtdeRepeticoes, Integer qtdeSeries, Integer cargaKG) {
		super();
		this.nome = nome;
		this.tipoExercicio = tipoExercicio;
		this.qtdeRepeticoes = qtdeRepeticoes;
		this.qtdeSeries = qtdeSeries;
		this.cargaKG = cargaKG;
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

	public Integer getCargaKG() {
		return cargaKG;
	}

	public void setCargaKG(Integer cargaKG) {
		this.cargaKG = cargaKG;
	}




	@Override
	public String toString() {
		return "Exercicio [id=" + id + ", nome=" + nome + ", tipoExercicio=" + tipoExercicio + ", qtdeRepeticoes="
				+ qtdeRepeticoes + ", qtdeSeries=" + qtdeSeries + ", cargaKG=" + cargaKG + "]";
	}
	
	
	
}
