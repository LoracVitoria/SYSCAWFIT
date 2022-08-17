package com.syscawfit.model;

public enum TipoPlano {
    Mensal("Mensal"),
    Trimestral("Trimestral"),
    Semestral("Semestral"),
    Anual("Anual");


    private String tipoPlano;

    TipoPlano(String tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public String getPlano() {
        return tipoPlano;
    }
}


