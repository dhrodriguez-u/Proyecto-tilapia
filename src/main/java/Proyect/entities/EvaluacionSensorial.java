package Proyect.entities;

public class EvaluacionSensorial {

    private String codigoLote;
    private int cantidadTilapias;
    private int ojos;
    private int branquias;
    private int cavidadAbdominal;
    private int olor;
    private int puntajeTotal;
    private String clasificacion;
    private String fechaEvaluacion;
    private String observaciones;

    // NUEVO ATRIBUTO: Mapea qué experto creó este registro para la privacidad
    private String creadorCorreo;

    // Constructor Vacío
    public EvaluacionSensorial() {
    }

    // Getters y Setters Estándar
    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public int getCantidadTilapias() {
        return cantidadTilapias;
    }

    public void setCantidadTilapias(int cantidadTilapias) {
        this.cantidadTilapias = cantidadTilapias;
    }

    public int getOjos() {
        return ojos;
    }

    public void setOjos(int ojos) {
        this.ojos = ojos;
    }

    public int getBranquias() {
        return branquias;
    }

    public void setBranquias(int branquias) {
        this.branquias = branquias;
    }

    public int getCavidadAbdominal() {
        return cavidadAbdominal;
    }

    public void setCavidadAbdominal(int cavidadAbdominal) {
        this.cavidadAbdominal = cavidadAbdominal;
    }

    public int getOlor() {
        return olor;
    }

    public void setOlor(int olor) {
        this.olor = olor;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    // CORREGIDO: Se elimina la línea que causaba el fallo del compilador
    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(String fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // NUEVO GETTER Y SETTER PARA EL CONTROL DE SESIÓN
    public String getCreadorCorreo() {
        return creadorCorreo;
    }

    public void setCreadorCorreo(String creadorCorreo) {
        this.creadorCorreo = creadorCorreo;
    }
}