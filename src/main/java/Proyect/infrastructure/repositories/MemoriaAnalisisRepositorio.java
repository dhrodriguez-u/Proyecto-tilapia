package Proyect.infrastructure.repositories;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemoriaAnalisisRepositorio implements AnalisisRepositorio {

    private static final List<EvaluacionSensorial> evaluaciones = new ArrayList<>();

    @Override
    public void guardar(EvaluacionSensorial evaluacion) {
        evaluaciones.add(evaluacion);
    }

    /**
     * CORRECCIÓN: Se sincroniza el nombre del método a 'obtenerTodas'
     * cumpliendo estrictamente con el contrato actualizado de AnalisisRepositorio.
     */
    @Override
    public List<EvaluacionSensorial> obtenerTodas() {
        return evaluaciones;
    }

    @Override
    public EvaluacionSensorial obtenerUltimoAnalisis() {
        return null;
    }

    /**
     * SOLUCIÓN AL ERROR: Implementación del método abstracto de la interfaz.
     * Filtra la lista en memoria para retornar solo las evaluaciones del experto logueado.
     */
    @Override
    public List<EvaluacionSensorial> obtenerTodasPorExperto(String correoExperto) {
        if (correoExperto == null) {
            return new ArrayList<>();
        }

        // Filtramos la lista estática en caliente comparando los correos creadores
        return evaluaciones.stream()
                .filter(eval -> correoExperto.equalsIgnoreCase(eval.getCreadorCorreo()))
                .collect(Collectors.toList());
    }
}
