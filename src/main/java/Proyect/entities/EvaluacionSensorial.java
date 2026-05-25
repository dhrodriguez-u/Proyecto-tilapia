package Proyect.entities;

public class EvaluacionSensorial {

    // Metadatos obligatorios del muestreo
    private String codigoLote;
    private int cantidadTilapias;

    // Descriptores oficiales según la tabla de la NTC 1443
    private int ojos;
    private int branquias;
    private int cavidadAbdominal; // Reemplaza a textura
    private int olor;

    private String observaciones;
    private int puntajeTotal;
    private String clasificacion;

    public EvaluacionSensorial() {}

    public EvaluacionSensorial(String codigoLote, int cantidadTilapias, int ojos, int branquias, int cavidadAbdominal, int olor, String observaciones) {
        this.codigoLote = codigoLote;
        this.cantidadTilapias = cantidadTilapias;
        this.ojos = ojos;
        this.branquias = branquias;
        this.cavidadAbdominal = cavidadAbdominal;
        this.olor = olor;
        this.observaciones = observaciones;
        this.puntajeTotal = ojos + branquias + cavidadAbdominal + olor;
    }

    // Getters y Setters
    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }

    public int getCantidadTilapias() { return cantidadTilapias; }
    public void setCantidadTilapias(int cantidadTilapias) { this.cantidadTilapias = cantidadTilapias; }

    public int getOjos() { return ojos; }
    public void setOjos(int ojos) { this.ojos = ojos; }

    public int getBranquias() { return branquias; }
    public void setBranquias(int branquias) { this.branquias = branquias; }

    public int getCavidadAbdominal() { return cavidadAbdominal; }
    public void setCavidadAbdominal(int cavidadAbdominal) { this.cavidadAbdominal = cavidadAbdominal; }

    public int getOlor() { return olor; }
    public void setOlor(int olor) { this.olor = olor; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(int puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
}