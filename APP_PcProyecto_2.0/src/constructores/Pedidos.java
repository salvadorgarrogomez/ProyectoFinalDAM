package constructores;

import java.util.Date;

public class Pedidos {

    private int ip_pedido;
    private Mesas nombre_mesa;
    private Productos nombre_producto;
    private Mesas cantidad_producto;
    private double precio_unitario;
    private double precio_total;
    private DetallesComanda num_comensales;
    private Usuarios usuario_id;
    private Date fecha_hora;

    public Pedidos() {
    }

    public Pedidos(int ip_pedido, Mesas nombre_mesa, Productos nombre_producto, Mesas cantidad_producto, double precio_unitario, double precio_total, DetallesComanda num_comensales, Usuarios usuario_id, Date fecha_hora) {
        this.ip_pedido = ip_pedido;
        this.nombre_mesa = nombre_mesa;
        this.nombre_producto = nombre_producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_unitario = precio_unitario;
        this.precio_total = precio_total;
        this.num_comensales = num_comensales;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
    }

    public int getIp_pedido() {
        return ip_pedido;
    }

    public void setIp_pedido(int ip_pedido) {
        this.ip_pedido = ip_pedido;
    }

    public Mesas getNombre_mesa() {
        return nombre_mesa;
    }

    public void setNombre_mesa(Mesas nombre_mesa) {
        this.nombre_mesa = nombre_mesa;
    }

    public Productos getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(Productos nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Mesas getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(Mesas cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    public DetallesComanda getNum_comensales() {
        return num_comensales;
    }

    public void setNum_comensales(DetallesComanda num_comensales) {
        this.num_comensales = num_comensales;
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
        return "Pedidos{" + "ip_pedido=" + ip_pedido + ", nombre_mesa=" + nombre_mesa + ", nombre_producto=" + nombre_producto + ", cantidad_producto=" + cantidad_producto + ", precio_unitario=" + precio_unitario + ", precio_total=" + precio_total + ", num_comensales=" + num_comensales + ", usuario_id=" + usuario_id + ", fecha_hora=" + fecha_hora + '}';
    }

}
