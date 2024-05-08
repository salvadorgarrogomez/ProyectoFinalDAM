package constructores;

import java.util.Arrays;
import java.util.Date;

public class TicketComanda {
    private int id;
    private Mesas nombre_mesa;
    private String num_ticket;
    private byte[] archivo_ticket;
    private int importe_total_sin_IVA;
    private int importe_total_con_IVA;
    private int num_comensales;
    private Usuarios usuario_id;
    private Date fecha_hora;

    public TicketComanda() {
    }

    public TicketComanda(int id, Mesas nombre_mesa, String num_ticket, byte[] archivo_ticket, int importe_total_sin_IVA, int importe_total_con_IVA, int num_comensales, Usuarios usuario_id, Date fecha_hora) {
        this.id = id;
        this.nombre_mesa = nombre_mesa;
        this.num_ticket = num_ticket;
        this.archivo_ticket = archivo_ticket;
        this.importe_total_sin_IVA = importe_total_sin_IVA;
        this.importe_total_con_IVA = importe_total_con_IVA;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
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

    public int getImporte_total_sin_IVA() {
        return importe_total_sin_IVA;
    }

    public void setImporte_total_sin_IVA(int importe_total_sin_IVA) {
        this.importe_total_sin_IVA = importe_total_sin_IVA;
    }

    public int getImporte_total_con_IVA() {
        return importe_total_con_IVA;
    }

    public void setImporte_total_con_IVA(int importe_total_con_IVA) {
        this.importe_total_con_IVA = importe_total_con_IVA;
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

    public int getNum_comensales() {
        return num_comensales;
    }

    public void setNum_comensales(int num_comensales) {
        this.num_comensales = num_comensales;
    }

    @Override
    public String toString() {
        return "TicketComanda{" + "id=" + id + ", nombre_mesa=" + nombre_mesa + ", num_ticket=" + num_ticket + ", archivo_ticket=" + Arrays.toString(archivo_ticket) + ", importe_total_sin_IVA=" + importe_total_sin_IVA + ", importe_total_con_IVA=" + importe_total_con_IVA + ", num_comensales=" + num_comensales + ", usuario_id=" + usuario_id + ", fecha_hora=" + fecha_hora + '}';
    }
    
}
