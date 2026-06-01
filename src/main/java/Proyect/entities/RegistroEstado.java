package Proyect.entities;

public class RegistroEstado {
    private int id;
    private String estacionNombre;
    private double temperatura;
    private double ph;
    private double oxigenoDisuelto;
    private double turbidez;
    private String observaciones;
    private String alerta;
    private String fechaHoraRegistro;

    // 🔒 NUEVO ATRIBUTO DE CONTROL DE AUDITORÍA Y PRIVACIDAD
    private String creadorCorreo;

    // Constructor Vacío
    public RegistroEstado() {}

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEstacionNombre() { return estacionNombre; }
    public void setEstacionNombre(String estacionNombre) { this.estacionNombre = estacionNombre; }

    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }

    public double getPh() { return ph; }
    public void setPh(double ph) { this.ph = ph; }

    public double getOxigenoDisuelto() { return oxigenoDisuelto; }
    public void setOxigenoDisuelto(double oxigenoDisuelto) { this.oxigenoDisuelto = oxigenoDisuelto; }

    public double getTurbidez() { return turbidez; }
    public void setTurbidez(double turbidez) { this.turbidez = turbidez; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getAlerta() { return alerta; }
    public void setAlerta(String alerta) { this.alerta = alerta; }

    public String getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(String fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro; }

    // 🔒 NUEVOS METODOS AGREGADOS PARA EL FILTRADO MULTIUSUARIO
    public String getCreadorCorreo() { return creadorCorreo; }
    public void setCreadorCorreo(String creadorCorreo) { this.creadorCorreo = creadorCorreo; }
}