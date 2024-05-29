package com.example.api_comandas.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuarios {
    @Id
    private Long id;
    private String nombre;
    private String rol;
    private String contrasenia;

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, String rol, String contrasenia) {
        this.id = (long) id;
        this.nombre = nombre;
        this.rol = rol;
        this.contrasenia = contrasenia;
    }

    public Long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
