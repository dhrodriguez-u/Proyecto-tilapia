package Proyect.usecases.ports;

import Proyect.entities.EvaluacionSensorial;
import java.util.List;

public interface ReporteRepository {
    List<EvaluacionSensorial> obtenerTodosLosAnalisis();
}