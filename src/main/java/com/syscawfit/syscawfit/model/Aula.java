package com.syscawfit.syscawfit.model;

import lombok.*;

import javax.persistence.*;
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

    @Embedded
    private AulaDiaHora aulaDiaHora;

    @NotNull
    private String nome;


    @ManyToOne(cascade = CascadeType.REMOVE)
    private Usuario professor;

}
