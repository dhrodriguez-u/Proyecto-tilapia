package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;
import java.util.List;

public class ConsultarReportesCalidadUseCase {

    private final AnalisisRepositorio analisisRepositorio;

    public ConsultarReportesCalidadUseCase(AnalisisRepositorio analisisRepositorio) {
        this.analisisRepositorio = analisisRepositorio;
    }

    /**
     * Recupera el histórico de evaluaciones de calidad organoléptica (NTC 1443).
     */
    public List<EvaluacionSensorial> obtenerReportesCalidad() {
        // Vinculado perfectamente con tu puerto original
        return analisisRepositorio.obtenerTodas();
    }
}
