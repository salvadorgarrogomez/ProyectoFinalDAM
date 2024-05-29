package constructores;

import java.util.Date;

public class Productos {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categorias categoria_id;
    private String tipo_plato;
    private String tipo_porcion;
    private boolean es_vegetariano;
    private boolean es_vegano;
    private boolean es_sin_gluten;
    private boolean es_sin_lactosa;
    private boolean es_picante;
    private Usuarios usuario_id;
    private Date fecha_hora;
    private RegistroAuditoria registroAuditoria;

    public Productos() {
    }

    public Productos(int id, String nombre, String descripcion, double precio, Categorias categoria_id, String tipo_plato, String tipo_porcion, boolean es_vegetariano, boolean es_vegano, boolean es_sin_gluten, boolean es_sin_lactosa, boolean es_picante, Usuarios usuario_id, Date fecha_hora) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria_id = categoria_id;
        this.tipo_plato = tipo_plato;
        this.tipo_porcion = tipo_porcion;
        this.es_vegetariano = es_vegetariano;
        this.es_vegano = es_vegano;
        this.es_sin_gluten = es_sin_gluten;
        this.es_sin_lactosa = es_sin_lactosa;
        this.es_picante = es_picante;
        this.usuario_id = usuario_id;
        this.fecha_hora = fecha_hora;
    }

    public Productos(int id, String nombre, double precio, String tipo_porcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_porcion = tipo_porcion;
    }

    public Productos(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Productos(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Productos(RegistroAuditoria registroAuditoria) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categorias getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Categorias categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getTipo_plato() {
        return tipo_plato;
    }

    public void setTipo_plato(String tipo_plato) {
        this.tipo_plato = tipo_plato;
    }

    public String getTipo_porcion() {
        return tipo_porcion;
    }

    public void setTipo_porcion(String tipo_porcion) {
        this.tipo_porcion = tipo_porcion;
    }

    public boolean isEs_vegetariano() {
        return es_vegetariano;
    }

    public void setEs_vegetariano(boolean es_vegetariano) {
        this.es_vegetariano = es_vegetariano;
    }

    public boolean isEs_vegano() {
        return es_vegano;
    }

    public void setEs_vegano(boolean es_vegano) {
        this.es_vegano = es_vegano;
    }

    public boolean isEs_sin_gluten() {
        return es_sin_gluten;
    }

    public void setEs_sin_gluten(boolean es_sin_gluten) {
        this.es_sin_gluten = es_sin_gluten;
    }

    public boolean isEs_sin_lactosa() {
        return es_sin_lactosa;
    }

    public void setEs_sin_lactosa(boolean es_sin_lactosa) {
        this.es_sin_lactosa = es_sin_lactosa;
    }

    public boolean isEs_picante() {
        return es_picante;
    }

    public void setEs_picante(boolean es_picante) {
        this.es_picante = es_picante;
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
        return id + " - " + nombre + " - " + precio + "€ - " + tipo_porcion;
    }

}
