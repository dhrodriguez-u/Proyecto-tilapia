package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.usecases.ports.RegistroEstadoRepositorio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConsultarEstacionUseCaseTest {

    @Test
    void obtenerHistorialEstacionesDebeRetornarListaCompletaDeRegistros() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        // Precondición: Creamos el set de datos simulados en memoria
        List<RegistroEstado> listaSimulada = new ArrayList<>();

        RegistroEstado registro1 = new RegistroEstado();
        registro1.setId(1);
        registro1.setEstacionNombre("Estanque Norte");
        registro1.setTemperatura(26.0);

        RegistroEstado registro2 = new RegistroEstado();
        registro2.setId(2);
        registro2.setEstacionNombre("Estanque Sur");
        registro2.setTemperatura(28.5);

        listaSimulada.add(registro1);
        listaSimulada.add(registro2);

        // Creamos la clase interna de prueba que implementa EXACTAMENTE tu interfaz real
        class RegistroEstadoRepositorioMock implements RegistroEstadoRepositorio {

            @Override
            public List<RegistroEstado> obtenerTodos() {
                return listaSimulada; // Retorna los datos controlados
            }

            @Override
            public void guardar(RegistroEstado registro) {
                // No se requiere lógica para simular una consulta
            }

            @Override
            public boolean actualizar(RegistroEstado registro) {
                return true; // Coincide exactamente con tu firma 'boolean'
            }

            @Override
            public boolean eliminar(int idRegistro) {
                return true; // Coincide exactamente con tu nombre de parámetro 'idRegistro'
            }
        }

        // Instanciamos el repositorio simulado y el caso de uso
        RegistroEstadoRepositorio repositorioMock = new RegistroEstadoRepositorioMock();
        ConsultarEstacionUseCase useCase = new ConsultarEstacionUseCase(repositorioMock);

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // Ejecutamos la consulta del servicio
        List<RegistroEstado> resultadoObtenido = useCase.obtenerHistorialEstaciones();

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // Postcondición: Validamos que la lista se entregue completa y sin alteraciones hídricas
        assertNotNull(resultadoObtenido, "El historial obtenido no puede ser nulo");
        assertEquals(2, resultadoObtenido.size(), "La lista debe contener exactamente los 2 registros simulados");
        assertEquals("Estanque Norte", resultadoObtenido.get(0).getEstacionNombre(), "El nombre del primer estanque debe coincidir");
        assertEquals(28.5, resultadoObtenido.get(1).getTemperatura(), 0.001, "Las variables de temperatura deben mantenerse íntegras");
    }
}

