package Proyect.entities;

public class LoteTilapia {
    private String idLote;
    private String procedencia;
    private String tipoTilapia;
    private String condicion;      // "Refrigerado" o "Al sol"
    private String coordenadas;
    private String altitud;
    private int cantidadIndividuos; // Guardará el tamaño de la muestra (ej. 20)

    public LoteTilapia() {}

    public LoteTilapia(String idLote, String procedencia, String tipoTilapia, String condicion, String coordenadas, String altitud, int cantidadIndividuos) {
        this.idLote = idLote;
        this.procedencia = procedencia;
        this.tipoTilapia = tipoTilapia;
        this.condicion = condicion;
        this.coordenadas = coordenadas;
        this.altitud = altitud;
        this.cantidadIndividuos = cantidadIndividuos;
    }

    // --- GETTERS Y SETTERS ---
    public String getIdLote() { return idLote; }
    public void setIdLote(String idLote) { this.idLote = idLote; }

    public String getProcedencia() { return procedencia; }
    public void setProcedencia(String procedencia) { this.procedencia = procedencia; }

    public String getTipoTilapia() { return tipoTilapia; }
    public void setTipoTilapia(String tipoTilapia) { this.tipoTilapia = tipoTilapia; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }

    public String getCoordenadas() { return coordenadas; }
    public void setCoordenadas(String coordenadas) { this.coordenadas = coordenadas; }

    public String getAltitud() { return altitud; }
    public void setAltitud(String altitud) { this.altitud = altitud; }

    public int getCantidadIndividuos() { return cantidadIndividuos; }
    public void setCantidadIndividuos(int cantidadIndividuos) { this.cantidadIndividuos = cantidadIndividuos; }
}
