package com.example.api_comandas.entidades;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mesas {
    @Id
    private Long id;
    private String nombre;
    private int comensales;
    private String estado;
    @ManyToOne
    private Usuarios usuario_id;
    private Date fecha_hora;
    @ManyToOne
    private RegistroAuditoria registroAuditoria;

    public Mesas() {
    }

    public Mesas(int id, String nombre, int comensales, String estado, Usuarios usuario_id, Date fecha_hora,
            RegistroAuditoria registroAuditoria) {
        this.id = (long) id;
        this.nombre = nombre;
        this.comensales = comensales;
        this.estado = estado;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
        this.registroAuditoria = registroAuditoria;
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

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuarios getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Usuarios usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public RegistroAuditoria getRegistroAuditoria() {
        return registroAuditoria;
    }

    public void setRegistroAuditoria(RegistroAuditoria registroAuditoria) {
        this.registroAuditoria = registroAuditoria;
    }

}
