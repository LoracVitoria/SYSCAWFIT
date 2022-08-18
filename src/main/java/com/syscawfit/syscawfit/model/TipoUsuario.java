package com.syscawfit.syscawfit.model;

public enum TipoUsuario {
    Funcionario("Funcionário"),
    Mantenedor("Mantenedor");
    private String tipoUsuario;

    TipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

}
