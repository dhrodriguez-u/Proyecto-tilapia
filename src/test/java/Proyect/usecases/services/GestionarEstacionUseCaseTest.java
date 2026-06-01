package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GestionarEstacionUseCaseTest {

    @Test
    void consultarRegistrosDebeRetornarListaCompletaDeEstaciones() {
        // 1. ARRANGE
        List<RegistroEstado> listaSimulada = new ArrayList<>();
        RegistroEstado e = new RegistroEstado();
        e.setEstacionNombre("Estanque Central");
        listaSimulada.add(e);

        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public List<RegistroEstado> obtenerTodos() { return listaSimulada; }
            @Override public void guardar(RegistroEstado r) {}
            @Override public boolean actualizar(RegistroEstado r) { return true; }
            @Override public boolean eliminar(int idRegistro) { return true; }
        }

        GestionarEstacionUseCase useCase = new GestionarEstacionUseCase(new RepositorioMock());

        // 2. ACT
        List<RegistroEstado> resultado = useCase.consultarRegistros();

        // 3. ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Estanque Central", resultado.get(0).getEstacionNombre());
    }

    @Test
    void actualizarRegistroDebeRetornarExitoCuandoLosDatosSonValidos() {
        // 1. ARRANGE
        RegistroEstado registroValido = new RegistroEstado();
        registroValido.setTemperatura(27.0);
        registroValido.setPh(7.2);

        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public void guardar(RegistroEstado r) {}
            @Override public boolean actualizar(RegistroEstado r) { return true; } // Simula éxito en DB
            @Override public boolean eliminar(int idRegistro) { return true; }
        }

        GestionarEstacionUseCase useCase = new GestionarEstacionUseCase(new RepositorioMock());

        // 2. ACT
        String mensaje = useCase.actualizarRegistro(registroValido);

        // 3. ASSERT
        assertEquals("El registro de la estación ha sido actualizado exitosamente.", mensaje);
    }

    @Test
    void actualizarRegistroDebeRetornarErrorCuandoLosParametrosSonInvalidos() {
        // 1. ARRANGE
        RegistroEstado registroInvalido = new RegistroEstado();
        registroInvalido.setTemperatura(-5.0); // Parámetro inválido
        registroInvalido.setPh(0.0);           // Parámetro inválido

        // No importa lo que devuelva el repositorio porque el 'if' frena la ejecución antes
        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public void guardar(RegistroEstado r) {}
            @Override public boolean actualizar(RegistroEstado r) { return false; }
            @Override public boolean eliminar(int idRegistro) { return false; }
        }

        GestionarEstacionUseCase useCase = new GestionarEstacionUseCase(new RepositorioMock());

        // 2. ACT
        String mensaje = useCase.actualizarRegistro(registroInvalido);

        // 3. ASSERT
        assertEquals("Error: Los parámetros de calidad del agua deben ser valores positivos válidos.", mensaje);
    }

    @Test
    void eliminarRegistroDebeRetornarExitoCuandoElIdEsValido() {
        // 1. ARRANGE
        int idValido = 10;
        String nombreEstanque = "Estanque Cosecha";

        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public void guardar(RegistroEstado r) {}
            @Override public boolean actualizar(RegistroEstado r) { return true; }
            @Override public boolean eliminar(int idRegistro) { return true; } // Simula eliminación exitosa
        }

        GestionarEstacionUseCase useCase = new GestionarEstacionUseCase(new RepositorioMock());

        // 2. ACT
        String mensaje = useCase.eliminarRegistro(idValido, nombreEstanque);

        // 3. ASSERT
        assertEquals("El registro de 'Estanque Cosecha' ha sido eliminado exitosamente.", mensaje);
    }

    @Test
    void eliminarRegistroDebeRetornarErrorCuandoElIdEsInvalido() {
        // 1. ARRANGE
        int idInvalido = 0; // ID inválido según la regla del caso de uso

        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public void guardar(RegistroEstado r) {}
            @Override public boolean actualizar(RegistroEstado r) { return true; }
            @Override public boolean eliminar(int idRegistro) { return true; }
        }

        GestionarEstacionUseCase useCase = new GestionarEstacionUseCase(new RepositorioMock());

        // 2. ACT
        String mensaje = useCase.eliminarRegistro(idInvalido, "Cualquiera");

        // 3. ASSERT
        assertEquals("Error: ID de registro inválido.", mensaje);
    }
}
