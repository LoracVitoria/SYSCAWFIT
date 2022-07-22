package com.syscawfit.syscawfit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cep;
    private String rua;
    private Long numero;
    private String bairro;
    private String cidade;
    private String UF;
    private String pais;

}
