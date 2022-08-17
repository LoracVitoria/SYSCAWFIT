package com.syscawfit.syscawfit.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


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

    @NotNull(message = "O campo tipo de plano deve ser preenchido.")
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private TipoPlano tipo;

    @Min(value = 10,message = "O valor de ser maior ou igual a 10.")
    private float valor;

}
