package Proyect.entities;

import java.time.LocalDateTime;

public class AceptacionNorma {
    private String correoUsuario;
    private String nombreNorma;
    private LocalDateTime fechaHoraAceptacion;
    private boolean terminosAceptados;

    public AceptacionNorma() {}

    public AceptacionNorma(String correoUsuario, String nombreNorma, boolean terminosAceptados) {
        this.correoUsuario = correoUsuario;
        this.nombreNorma = nombreNorma;
        this.terminosAceptados = terminosAceptados;
        this.fechaHoraAceptacion = LocalDateTime.now(); // Marca de tiempo del sistema automático
    }

    // --- GETTERS Y SETTERS ---
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public String getNombreNorma() { return nombreNorma; }
    public void setNombreNorma(String nombreNorma) { this.nombreNorma = nombreNorma; }

    public LocalDateTime getFechaHoraAceptacion() { return fechaHoraAceptacion; }
    public void setFechaHoraAceptacion(LocalDateTime fechaHoraAceptacion) { this.fechaHoraAceptacion = fechaHoraAceptacion; }

    public boolean isTerminosAceptados() { return terminosAceptados; }
    public void setTerminosAceptados(boolean terminosAceptados) { this.terminosAceptados = terminosAceptados; }
}
