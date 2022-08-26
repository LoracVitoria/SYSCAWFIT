package com.syscawfit.syscawfit.model;

public enum Genero {
    Feminino("Feminino"),
    Masculino("Masculino"),
    Outro("Outro");

    private String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}
