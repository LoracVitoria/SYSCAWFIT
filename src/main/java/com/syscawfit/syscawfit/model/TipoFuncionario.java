package com.syscawfit.syscawfit.model;

public enum TipoFuncionario {
    Administracao("Administração"),
    Recepcao("Recepção"),
    RH("RH"),
    Treinador("Treinador"),
    Limpeza("Limpeza"),
    Financeiro("Financeiro");
    private final String tipoFuncionario;

    TipoFuncionario(String tipoFuncionario) {

        this.tipoFuncionario = tipoFuncionario;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }
}
