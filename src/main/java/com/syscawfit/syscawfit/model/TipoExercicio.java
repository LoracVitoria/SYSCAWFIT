package com.syscawfit.syscawfit.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class TipoExercicio {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_equipamento")
	private Equipamentos equipamento;
	
	
	public TipoExercicio() {
	}
	

	public TipoExercicio(String nome, Equipamentos equipamento) {
		this.nome = nome;
		this.equipamento = equipamento;
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

	public Equipamentos getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamentos equipamento) {
		this.equipamento = equipamento;
	}


	@Override
	public String toString() {
		return "TipoExercicio [id=" + id + ", nome=" + nome + ", equipamento=" + equipamento + ", getId()=" + getId()
				+ ", getNome()=" + getNome() + ", getEquipamento()=" + getEquipamento() + "]";
	}
	
	
	
	
}
