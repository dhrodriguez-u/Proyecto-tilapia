package Proyect.infrastructure.repositories;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;

import java.util.ArrayList;
import java.util.List;

public class MemoriaAnalisisRepositorio
        implements AnalisisRepositorio {

    private static final List<EvaluacionSensorial>
            evaluaciones = new ArrayList<>();

    @Override
    public void guardar(
            EvaluacionSensorial evaluacion
    ) {

        evaluaciones.add(evaluacion);
    }

    @Override
    public List<EvaluacionSensorial> obtenerTodos() {
        return evaluaciones;
    }
}
