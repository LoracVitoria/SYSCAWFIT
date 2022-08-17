package com.syscawfit.syscawfit.model;

import lombok.Data;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Embeddable
public class AulaDiaHora implements Serializable {

    private static final long serialVersionUID = 3067070130719740930L;

    @NotNull(message = "O campo dia da semana deve ser preenchido.")
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @NotNull(message = "O campo hora deve ser preenchido.")
    private LocalTime hora;
}
