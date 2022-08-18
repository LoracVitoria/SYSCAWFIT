package com.syscawfit.syscawfit.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"diaSemana", "hora"})})
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotBlank(message = "O campo deve se preenchido.")
    @Embedded
    private AulaDiaHora aulaDiaHora;

    @NotBlank(message = "O campo nome deve ser preenchido.")
    private String nome;

    @NotNull(message = "O campo professor deve ser preenchido.")
    @ManyToOne
    private Usuario professor;

}
