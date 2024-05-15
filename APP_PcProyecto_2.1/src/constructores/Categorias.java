package constructores;

import java.util.Date;

public class Categorias {

    private int id;
    private String nombre;
    private Usuarios usuario_id;
    private Date fecha_hora;
    private RegistroAuditoria registroAuditoria;

    public Categorias() {
    }

    public Categorias(int id, String nombre, Usuarios usuario_id, Date fecha_hora, RegistroAuditoria registroAuditoria) {
        this.id = id;
        this.nombre = nombre;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
        this.registroAuditoria = registroAuditoria;
    }

    public Categorias(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre;
    }

}
