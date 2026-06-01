package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RegistrarEstadoUseCaseTest {

    @Test
    void ejecutarDebeMarcarParametrosOptimosCuandoTodoEstaEnRango() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        List<RegistroEstado> dbSimulada = new ArrayList<>();

        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override
            public void guardar(RegistroEstado registro) {
                dbSimulada.add(registro); // Simula el insert en la base de datos
            }
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public boolean actualizar(RegistroEstado registro) { return true; }
            @Override public boolean eliminar(int idRegistro) { return true; }
        }

        RegistrarEstadoUseCase useCase = new RegistrarEstadoUseCase(new RepositorioMock());

        // Configuramos parámetros perfectos (Temp: 27°C, pH: 7.2, Oxígeno: 6.0)
        RegistroEstado registroPerfecto = new RegistroEstado();
        registroPerfecto.setTemperatura(27.0);
        registroPerfecto.setPh(7.2);
        registroPerfecto.setOxigenoDisuelto(6.0);

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        String resultadoAlerta = useCase.ejecutar(registroPerfecto);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertEquals("Parámetros Óptimos", resultadoAlerta, "Si los valores están bien, debe devolver 'Parámetros Óptimos'");
        assertEquals("Parámetros Óptimos", registroPerfecto.getAlerta(), "El objeto debe almacenar internamente el estado óptimo");
        assertEquals(1, dbSimulada.size(), "El registro debió guardarse de forma exitosa en el repositorio");
    }

    @Test
    void ejecutarDebeConcatenarAlertasCuandoLosParametrosSonCriticos() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        class RepositorioMock implements RegistroEstadoRepositorio {
            @Override public void guardar(RegistroEstado registro) {}
            @Override public List<RegistroEstado> obtenerTodos() { return null; }
            @Override public boolean actualizar(RegistroEstado registro) { return true; }
            @Override public boolean eliminar(int idRegistro) { return true; }
        }

        RegistrarEstadoUseCase useCase = new RegistrarEstadoUseCase(new RepositorioMock());

        // Configuramos un caso crítico donde falla TODO (Temp alta, pH bajo, Oxígeno bajo)
        RegistroEstado registroCritico = new RegistroEstado();
        registroCritico.setTemperatura(35.0);        // Fuera de rango (Óptimo: 24-32)
        registroCritico.setPh(5.0);                 // Fuera de rango (Óptimo: 6.5-8.5)
        registroCritico.setOxigenoDisuelto(3.0);    // Crítico (Óptimo: >= 5)

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        String resultadoAlerta = useCase.ejecutar(registroCritico);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // El StringBuilder debió concatenar las tres alertas en orden
        assertTrue(resultadoAlerta.contains("[Temperatura fuera de rango (Óptimo: 24-32°C)]"));
        assertTrue(resultadoAlerta.contains("[pH crítico (Óptimo: 6.5-8.5)]"));
        assertTrue(resultadoAlerta.contains("[Oxígeno Disuelto bajo (Óptimo: >= 5 mg/L)]"));
    }
}