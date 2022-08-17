package com.syscawfit.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private AulaDiaHora aulaDiaHora;

    @NotNull
    private String nome;


    @ManyToOne(cascade = CascadeType.REMOVE)
    private Usuario professor;

}
