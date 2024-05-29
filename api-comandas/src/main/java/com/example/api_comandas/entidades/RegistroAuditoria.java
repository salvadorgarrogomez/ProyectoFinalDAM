package com.example.api_comandas.entidades;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RegistroAuditoria {
    @Id
    private Long id;
    private String tabla_afectada;
    private int id_registro_afectado;
    private int id_usuario;
    private String tipo_operacion;
    private Date fecha_hora;

    public RegistroAuditoria() {
    }

    public RegistroAuditoria(int id, String tabla_afectada, int id_registro_afectado, int id_usuario,
            String tipo_operacion, Date fecha_hora) {
        this.id = (long) id;
        this.tabla_afectada = tabla_afectada;
        this.id_registro_afectado = id_registro_afectado;
        this.id_usuario = id_usuario;
        this.tipo_operacion = tipo_operacion;
        this.fecha_hora = fecha_hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public String getTabla_afectada() {
        return tabla_afectada;
    }

    public void setTabla_afectada(String tabla_afectada) {
        this.tabla_afectada = tabla_afectada;
    }

    public int getId_registro_afectado() {
        return id_registro_afectado;
    }

    public void setId_registro_afectado(int id_registro_afectado) {
        this.id_registro_afectado = id_registro_afectado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

}
