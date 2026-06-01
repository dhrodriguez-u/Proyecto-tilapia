package Proyect.usecases.ports;

import Proyect.entities.RegistroEstado;
import java.util.List;

public interface RegistroEstadoRepositorio {

    // Método que ya tenías para crear un nuevo registro
    void guardar(RegistroEstado registro);

    // Método que ya tenías para leer y llenar la tabla
    List<RegistroEstado> obtenerTodos();

    // --- NUEVOS MÉTODOS PARA EL CASO DE USO DE GESTIÓN ---

    // Método para editar/actualizar un registro existente
    boolean actualizar(RegistroEstado registro);

    // Método para borrar un registro usando su ID
    boolean eliminar(int idRegistro);
}
