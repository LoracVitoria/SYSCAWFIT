package com.syscawfit.model;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Embeddable
public class AulaDiaHora implements Serializable {

    private static final long serialVersionUID = 3067070130719740930L;

    @NotBlank(message = "O campo dia da semana deve ser preenchido.")
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

//    @NotBlank(message = "O campo hora deve ser preenchido.")
    private LocalTime hora;
}
