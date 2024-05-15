package constructores;

import java.util.Arrays;
import java.util.Date;

public class TicketComanda {
    private int id;
    private Mesas nombre_mesa;
    private String num_ticket;
    private byte[] archivo_ticket;
    private double importe_total_sin_IVA;
    private double importe_total_con_IVA;
    private int num_comensales;
    private Usuarios usuario_id;
    private Date fecha_pedido;

    public TicketComanda() {
    }

    public TicketComanda(int id, Mesas nombre_mesa, String num_ticket, byte[] archivo_ticket, double importe_total_sin_IVA, double importe_total_con_IVA, int num_comensales, Usuarios usuario_id, Date fecha_pedido) {
        this.id = id;
        this.nombre_mesa = nombre_mesa;
        this.num_ticket = num_ticket;
        this.archivo_ticket = archivo_ticket;
        this.importe_total_sin_IVA = importe_total_sin_IVA;
        this.importe_total_con_IVA = importe_total_con_IVA;
        this.usuario_id = usuario_id;
        this.fecha_pedido = fecha_pedido;
        this.num_comensales = num_comensales;
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

    public String getNum_ticket() {
        return num_ticket;
    }

    public void setNum_ticket(String num_ticket) {
        this.num_ticket = num_ticket;
    }

    public byte[] getArchivo_ticket() {
        return archivo_ticket;
    }

    public void setArchivo_ticket(byte[] archivo_ticket) {
        this.archivo_ticket = archivo_ticket;
    }

    public double getImporte_total_sin_IVA() {
        return importe_total_sin_IVA;
    }

    public void setImporte_total_sin_IVA(double importe_total_sin_IVA) {
        this.importe_total_sin_IVA = importe_total_sin_IVA;
    }

    public double getImporte_total_con_IVA() {
        return importe_total_con_IVA;
    }

    public void setImporte_total_con_IVA(double importe_total_con_IVA) {
        this.importe_total_con_IVA = importe_total_con_IVA;
    }

    public Usuarios getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Usuarios usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public int getNum_comensales() {
        return num_comensales;
    }

    public void setNum_comensales(int num_comensales) {
        this.num_comensales = num_comensales;
    }

    @Override
    public String toString() {
        return nombre_mesa + " (" + num_ticket + ") " + ", Importe sin IVA: " + importe_total_sin_IVA + "€, Importe con IVA: " + importe_total_con_IVA  + "€, Comensales: " + num_comensales + " - " + fecha_pedido;
    }
    
    
    
}
