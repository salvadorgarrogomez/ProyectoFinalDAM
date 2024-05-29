package constructores;

import java.util.Date;

public class Mesas {

    private int id;
    private String nombre;
    private int comensales;
    private String estado;
    private Usuarios usuario_id;
    private Date fecha_hora;
    private RegistroAuditoria registroAuditoria;

    public Mesas() {
    }

    public Mesas(int id, String nombre, int comensales, String estado, Usuarios usuario_id, Date fecha_hora) {
        this.id = id;
        this.nombre = nombre;
        this.comensales = comensales;
        this.estado = estado;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
    }

    public Mesas(String nombre) {
        this.nombre = nombre;
    }

    public Mesas(RegistroAuditoria registroAuditoria) {
        this.registroAuditoria = registroAuditoria;
    }

    public RegistroAuditoria getRegistroAuditoria() {
        return registroAuditoria;
    }

    public void setRegistroAuditoria(RegistroAuditoria registroAuditoria) {
        this.registroAuditoria = registroAuditoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return nombre;
    }

}
