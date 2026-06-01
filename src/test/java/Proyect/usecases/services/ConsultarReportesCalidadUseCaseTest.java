package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConsultarReportesCalidadUseCaseTest {

    @Test
    void obtenerReportesCalidadDebeRetornarListaDeEvaluacionesSensoriales() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        List<EvaluacionSensorial> listaSimulada = new ArrayList<>();

        EvaluacionSensorial evaluacion1 = new EvaluacionSensorial();
        evaluacion1.setCodigoLote("LOTE-01");
        evaluacion1.setOjos(5);
        evaluacion1.setOlor(5);

        EvaluacionSensorial evaluacion2 = new EvaluacionSensorial();
        evaluacion2.setCodigoLote("LOTE-02");
        evaluacion2.setOjos(3);
        evaluacion2.setOlor(2);

        listaSimulada.add(evaluacion1);
        listaSimulada.add(evaluacion2);

        // Creamos la clase de simulación ajustada EXACTAMENTE a tu interfaz
        class AnalisisRepositorioMock implements AnalisisRepositorio {
            @Override
            public List<EvaluacionSensorial> obtenerTodas() {
                return listaSimulada;
            }

            @Override
            public EvaluacionSensorial obtenerUltimoAnalisis() {
                return null;
            }

            // 🔒 SOLUCIÓN: Implementamos el método del contrato exigido para evitar el error de compilación
            @Override
            public List<EvaluacionSensorial> obtenerTodasPorExperto(String expertoCorreo) {
                return listaSimulada;
            }

            // --- MÉTODOS COMPLEMENTARIOS SEGUROS ---
            @Override
            public void guardar(EvaluacionSensorial evaluacion) {}
        }

        // Instanciamos el mock seguro y el caso de uso
        AnalisisRepositorio repositorioMock = new AnalisisRepositorioMock();
        ConsultarReportesCalidadUseCase useCase = new ConsultarReportesCalidadUseCase(repositorioMock);

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        List<EvaluacionSensorial> resultadoObtenido = useCase.obtenerReportesCalidad();

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertNotNull(resultadoObtenido, "La lista de reportes de calidad no puede ser nula");
        assertEquals(2, resultadoObtenido.size(), "Debe retornar exactamente las 2 evaluaciones simuladas");
        assertEquals("LOTE-01", resultadoObtenido.get(0).getCodigoLote(), "El código del primer lote debe coincidir");
        assertEquals(5, resultadoObtenido.get(0).getOjos(), "Las variables sensoriales de ojos deben permanecer intactas");
        assertEquals(2, resultadoObtenido.get(1).getOlor(), "Las variables sensoriales de olor deben permanecer intactas");
    }
}
