package Proyect.usecases.ports;

import Proyect.entities.EvaluacionSensorial;

import java.util.List;

public interface AnalisisRepositorio {

    void guardar(EvaluacionSensorial evaluacion);

    List<EvaluacionSensorial> obtenerTodos();
}