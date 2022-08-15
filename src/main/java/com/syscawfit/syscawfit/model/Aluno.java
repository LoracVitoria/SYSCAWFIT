package com.syscawfit.syscawfit.model;

import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O campo nome deve ser preenchido")
    private String nome;

    @Column(unique = true)
    private String cpf;

    private String imagemAluno;

    private String telefone;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    private TipoPlano plano;

    private LocalDate dataIngresso = LocalDate.now();

    @OneToOne(cascade = CascadeType.REMOVE)
    private EnderecoAluno endereco;


}
