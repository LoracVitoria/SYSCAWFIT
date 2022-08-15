package com.syscawfit.syscawfit.model;

import lombok.Data;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Embeddable
public class AulaDiaHora implements Serializable {

    private static final long serialVersionUID = 3067070130719740930L;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    private LocalTime hora;
}
