package com.syscawfit.model;

import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
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

    @NotBlank(message = "O campo nome deve ser preenchido.")
    private String nome;

    @NotBlank(message = "O campo CPF deve ser preenchido.")
    @Column(unique = true)
    private String cpf;

    @Size(max=1000)
    private String imagemAluno;

    @NotBlank(message = "O campo telefone deve ser preenchido.")
    private String telefone;

    @NotBlank(message = "O campo e-mail deve ser preenchido.")
    @Email(message = "Endereço de e-mail enviado em um formato inválido. ")
    private String email;

    @NotNull(message = "O campo data de nascimento não deve ser nulo.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @NotNull(message = "O campo gênero não deve ser vazio.")
    private Genero genero;

    @NotNull (message = "O campo tipo de plano não deve ser vazio.")
    private TipoPlano plano;

    private LocalDate dataIngresso = LocalDate.now();

    @Valid
    @OneToOne(cascade = CascadeType.REMOVE)
    private EnderecoAluno endereco;

}
