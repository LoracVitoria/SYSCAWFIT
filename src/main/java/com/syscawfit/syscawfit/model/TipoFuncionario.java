package com.syscawfit.syscawfit.model;

public enum TipoFuncionario {
    Administrativo("Administrativo"),
    Recepcionista("Recepcionista"),
    Treinador("Treinador"),
    Financeiro("Financeiro");
    private final String tipoFuncionario;

    TipoFuncionario(String tipoFuncionario) {

        this.tipoFuncionario = tipoFuncionario;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }
}
