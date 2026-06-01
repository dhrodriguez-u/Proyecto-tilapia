package Proyect.usecases.ports;

import Proyect.entities.Usuario;
import java.util.List;

public interface UsuarioRepositorio {
    void guardar(Usuario usuario);
    List<Usuario> obtenerTodos();
    void eliminar(String correo);
    // Nuevo método para búsqueda eficiente
    Usuario obtenerPorCorreo(String correo);
}
