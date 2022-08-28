package com.syscawfit.syscawfit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Equipamentos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank(message = "campo obrigatório")
	@Size(min = 2, max = 100, message = "Campo deve conter entre {min} e {max} carácteres")
	private String nome;
	
	@Column(columnDefinition = "VARCHAR(2000)")
	@Size(max = 1000)
	private String descricao;
	
	@Size(max = 1000)
	private String nomeImagem;
	

	public Equipamentos() {}
	

	public Equipamentos(Long id,
			@NotNull @NotBlank(message = "campo obrigatório") @Size(min = 2, max = 100, message = "Campo deve conter entre {min} e {max} carácteres") String nome,
			@Size(max = 1000) String descricao, @Size(max = 1000) String nomeImagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.nomeImagem = nomeImagem;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	@Override
	public String toString() {
		return "Equipamentos [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", nomeImagem=" + nomeImagem
				+ "]";
	}
	
	

}
