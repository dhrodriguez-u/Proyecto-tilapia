package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import java.util.List;

public class ConsultarEstacionUseCase {

    private final RegistroEstadoRepositorio registroEstadoRepositorio;

    public ConsultarEstacionUseCase(RegistroEstadoRepositorio registroEstadoRepositorio) {
        this.registroEstadoRepositorio = registroEstadoRepositorio;
    }

    /**
     * Recupera el histórico completo de condiciones ambientales para la auditoría del Investigador.
     */
    public List<RegistroEstado> obtenerHistorialEstaciones() {
        return registroEstadoRepositorio.obtenerTodos();
    }
}

