package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.ReporteRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GenerarReporteUseCase {

    private final ReporteRepository reporteRepository;

    public GenerarReporteUseCase(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * Escribe el documento en disco aplicando el filtro de seguridad por rol y usuario.
     * * @param listaEvaluaciones Lista con todas las evaluaciones que vienen de la vista.
     * @param destino Archivo físico .txt donde se guardará.
     * @param correoUsuarioLogueado Correo de la sesión activa en el programa.
     * @param rolUsuarioLogueado Rol de la sesión activa ("INVESTIGADOR", "EXPERTO").
     */
    public boolean exportarATextoPlano(List<EvaluacionSensorial> listaEvaluaciones, File destino, String correoUsuarioLogueado, String rolUsuarioLogueado) {
        if (listaEvaluaciones == null || listaEvaluaciones.isEmpty()) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {
            writer.write("=====================================================================");
            writer.newLine();
            writer.write("   SISTEMA SENSORIAL TILAPIA - REPORTE DE AUDITORÍA (NTC 1443)       ");
            writer.newLine();
            writer.write("=====================================================================");
            writer.newLine();
            writer.newLine();

            // Encabezado de auditoría para saber quién bajó el archivo
            writer.write("Generado por:      " + correoUsuarioLogueado + " [" + rolUsuarioLogueado + "]");
            writer.newLine();
            writer.write("---------------------------------------------------------------------");
            writer.newLine();
            writer.newLine();

            for (EvaluacionSensorial eval : listaEvaluaciones) {

                // 🔒 FILTRO DE PRIVACIDAD EN CALIENTE:
                // Si el usuario NO es INVESTIGADOR (es decir, es un Experto Evaluador),
                // y el lote tiene un creador asignado que NO coincide con su correo, lo ignoramos.
                if (!"INVESTIGADOR".equalsIgnoreCase(rolUsuarioLogueado)) {
                    if (eval.getCreadorCorreo() != null && !eval.getCreadorCorreo().equalsIgnoreCase(correoUsuarioLogueado)) {
                        continue; // Salta este registro y pasa al siguiente lote sin escribir nada
                    }
                }

                writer.write("Código de Lote:    " + eval.getCodigoLote()); writer.newLine();
                writer.write("Cantidad Muestra:  " + eval.getCantidadTilapias() + " unidades"); writer.newLine();
                writer.write("Puntaje Total:     " + eval.getPuntajeTotal() + " / 20"); writer.newLine();
                writer.write("Clasificación NTC: " + eval.getClasificacion()); writer.newLine();
                writer.write("Observaciones:     " + eval.getObservaciones()); writer.newLine();
                writer.write("---------------------------------------------------------------------");
                writer.newLine();
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el reporte .txt: " + e.getMessage());
            return false;
        }
    }
}