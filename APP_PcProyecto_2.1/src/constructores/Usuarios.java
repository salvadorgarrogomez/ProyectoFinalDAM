package constructores;

public class Usuarios {

    private int id;
    private String nombre;
    private String rol;
    private String contrasenia;

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, String rol, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.contrasenia = contrasenia;
    }

    public Usuarios(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Usuarios(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuarios(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return this.nombre + " - " + this.rol;
    }

}
