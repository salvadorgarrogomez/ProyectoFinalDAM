package constructores;

import java.util.Date;

public class DetallesComanda {

    private int id;
    private Mesas nombre_mesa;
    private Productos producto_id;
    private int cantidad;
    private Usuarios usuario_id;
    private Date fecha_hora;
    private RegistroAuditoria registroAuditoria;

    public DetallesComanda() {
    }

    public DetallesComanda(int id, Mesas nombre_mesa, Productos producto_id, int cantidad, Usuarios usuario_id, Date fecha_hora) {
        this.id = id;
        this.nombre_mesa = nombre_mesa;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
    }

    public DetallesComanda(Mesas nombre_mesa, Productos producto_id, int cantidad) {
        this.nombre_mesa = nombre_mesa;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
    }

    public DetallesComanda(RegistroAuditoria registroAuditoria) {
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

    public Mesas getNombre_mesa() {
        return nombre_mesa;
    }

    public void setNombre_mesa(Mesas nombre_mesa) {
        this.nombre_mesa = nombre_mesa;
    }

    public Productos getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Productos producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        return nombre_mesa + " - " + producto_id + " - " + cantidad;
    }

}
