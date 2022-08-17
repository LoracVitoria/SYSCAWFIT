package com.syscawfit.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotEmpty(message = "O campo tipo de plano não deve ser vazio.")
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private TipoPlano tipo;

//    @NotEmpty(message = "O campo valor não deve ser vazio.")
    private float valor;

}
