package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.entities.Usuario;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import java.util.List;
import java.util.stream.Collectors;

public class GestionarEstacionUseCase {

    private final RegistroEstadoRepositorio repositorio;

    public GestionarEstacionUseCase(RegistroEstadoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Consulta registros aplicando el filtro de privacidad por usuario y rol.
     */
    public List<RegistroEstado> consultarRegistros(Usuario usuarioLogueado) {
        List<RegistroEstado> todos = repositorio.obtenerTodos();

        if (usuarioLogueado == null) {
            return todos;
        }

        // Si es investigador, tiene acceso total a todos los estanques
        if ("INVESTIGADOR".equalsIgnoreCase(usuarioLogueado.getRol())) {
            return todos;
        }

        // Si es Piscicultor, solo retornamos las estaciones creadas por él
        return todos.stream()
                .filter(r -> r.getCreadorCorreo() != null && r.getCreadorCorreo().equalsIgnoreCase(usuarioLogueado.getCorreo()))
                .collect(Collectors.toList());
    }

    public List<RegistroEstado> consultarRegistros() {
        return repositorio.obtenerTodos();
    }

    public String actualizarRegistro(RegistroEstado registroModificado) {
        if (registroModificado.getTemperatura() <= 0 || registroModificado.getPh() <= 0) {
            return "Error: Los parámetros de calidad del agua deben ser valores positivos válidos.";
        }

        boolean exito = repositorio.actualizar(registroModificado);
        return exito ? "El registro de la estación ha sido actualizado exitosamente."
                : "Error interno al intentar actualizar en la base de datos.";
    }

    public String eliminarRegistro(int idRegistro, String nombreEstanque) {
        if (idRegistro <= 0) {
            return "Error: ID de registro inválido.";
        }

        boolean exito = repositorio.eliminar(idRegistro);
        return exito ? "El registro de '" + nombreEstanque + "' ha sido eliminado exitosamente."
                : "Error al intentar eliminar el registro.";
    }
}
