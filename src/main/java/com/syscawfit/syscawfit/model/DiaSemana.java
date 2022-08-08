package com.syscawfit.syscawfit.model;


public enum DiaSemana {
    Segunda("Segunda"),
    Terça("Terça"),
    Quarta("Quarta"),
    Quinta("Quinta"),
    Sexta("Sexta"),
    Sábado("Sábado");


    private String dia;

    DiaSemana(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }
}
