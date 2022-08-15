package com.syscawfit.syscawfit.model;

import lombok.*;

import javax.persistence.*;

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

    private String nome;

    @ManyToOne
    private Usuario professor;

}
