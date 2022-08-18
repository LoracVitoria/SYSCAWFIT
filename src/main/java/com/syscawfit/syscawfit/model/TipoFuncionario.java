package com.syscawfit.syscawfit.model;

public enum TipoFuncionario {
    Administrador("Administrador"),
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

//    @Override
//    public String toString() {
//        return this.tipoFuncionario;
//    }
}
