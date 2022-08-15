package com.syscawfit.syscawfit.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EnderecoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cep;
    private String logradouro;
    private Long numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String pais;

}
