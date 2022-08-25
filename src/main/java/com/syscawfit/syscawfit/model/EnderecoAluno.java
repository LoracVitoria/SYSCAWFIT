package com.syscawfit.syscawfit.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotBlank(message = "O campo CEP deve ser preenchido.")
    private String cep;

    @NotBlank(message = "O campo logradouro deve ser preenchido.")
    private String logradouro;

    @NotNull(message = "O campo Nº deve ser preenchido.")
    private Long numero;

    @NotBlank(message = "O campo bairro deve ser preenchido.")
    private String bairro;

    @NotBlank(message = "O campo cidade deve ser preenchido.")
    private String cidade;

    @NotBlank(message = "O campo UF deve ser preenchido.")
    private String uf;

    @NotBlank(message = "O campo País deve ser preenchido.")
    private String pais;

}
