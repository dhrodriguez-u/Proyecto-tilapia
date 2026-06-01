package Proyect.usecases.ports;

import Proyect.entities.EvaluacionSensorial;
import java.util.List;

public interface AnalisisRepositorio {

    void guardar(EvaluacionSensorial eval);

    List<EvaluacionSensorial> obtenerTodas();

    EvaluacionSensorial obtenerUltimoAnalisis();

    /**
     * NUEVO MÉTODO: Filtra el histórico para garantizar que un experto
     * evaluador solo recupere de la base de datos sus propios registros.
     */
    List<EvaluacionSensorial> obtenerTodasPorExperto(String correoExperto);
}