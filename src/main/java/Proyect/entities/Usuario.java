package Proyect.entities;

public class Usuario {
    private String nombre;
    private String correo;
    private String password;
    private String rol;

    public Usuario() {}

    // Constructor completo
    public Usuario(String nombre, String correo, String password, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }

    // CORRECCIÓN: Quitamos el "default" que puede estar sobrescribiendo tu selección
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}