package com.example.app_movilproyecto;

public class Mesa {
    private int numero;
    private String estado;

    public Mesa(int numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }
}

