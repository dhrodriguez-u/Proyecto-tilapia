package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RealizarAnalisisUseCaseTest {

    @Test
    void ejecutarDebeCalcularPuntajeYClasificarComoExcelenteCalidad() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        List<EvaluacionSensorial> baseDatosSimulada = new ArrayList<>();

        class AnalisisRepositorioMock implements AnalisisRepositorio {
            @Override
            public void guardar(EvaluacionSensorial evaluacion) {
                baseDatosSimulada.add(evaluacion); // Simula el INSERT INTO en MySQL
            }

            @Override
            public List<EvaluacionSensorial> obtenerTodas() { return baseDatosSimulada; }

            @Override
            public EvaluacionSensorial obtenerUltimoAnalisis() { return null; }

            // 🔒 SOLUCIÓN: Implementamos el método del contrato para que no falle la compilación
            @Override
            public List<EvaluacionSensorial> obtenerTodasPorExperto(String expertoCorreo) {
                return baseDatosSimulada;
            }
        }

        RealizarAnalisisUseCase useCase = new RealizarAnalisisUseCase(new AnalisisRepositorioMock());

        // Datos de una tilapia en estado óptimo (Suma: 5 + 4 + 4 + 4 = 17)
        EvaluacionSensorial muestraExcelente = new EvaluacionSensorial();
        muestraExcelente.setCodigoLote("LOTE-TOP-01");
        muestraExcelente.setOjos(5);
        muestraExcelente.setBranquias(4);
        muestraExcelente.setCavidadAbdominal(4);
        muestraExcelente.setOlor(4);

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        useCase.ejecutar(muestraExcelente);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertEquals(17, muestraExcelente.getPuntajeTotal(), "El puntaje total debió calcularse y fijarse en 17");
        assertEquals("Excelente calidad", muestraExcelente.getClasificacion(), "17 puntos equivalen a 'Excelente calidad'");
        assertEquals(1, baseDatosSimulada.size(), "El objeto debió persistirse dentro del repositorio");
        assertEquals("LOTE-TOP-01", baseDatosSimulada.get(0).getCodigoLote());
    }

    @Test
    void ejecutarDebeCalcularPuntajeYClasificarComoProductoNoRecomendable() {
        // 1. ARRANGE
        class AnalisisRepositorioMock implements AnalisisRepositorio {
            @Override public void guardar(EvaluacionSensorial e) {}
            @Override public List<EvaluacionSensorial> obtenerTodas() { return null; }
            @Override public EvaluacionSensorial obtenerUltimoAnalisis() { return null; }

            // 🔒 SOLUCIÓN: Implementamos el método exigido por la interfaz
            @Override public List<EvaluacionSensorial> obtenerTodasPorExperto(String expertoCorreo) { return null; }
        }

        RealizarAnalisisUseCase useCase = new RealizarAnalisisUseCase(new AnalisisRepositorioMock());

        // Datos de una tilapia en estado de descomposición avanzada (Suma: 1 + 2 + 0 + 1 = 4)
        EvaluacionSensorial muestraDefectuosa = new EvaluacionSensorial();
        muestraDefectuosa.setOjos(1);
        muestraDefectuosa.setBranquias(2);
        muestraDefectuosa.setCavidadAbdominal(0);
        muestraDefectuosa.setOlor(1);

        // 2. ACT
        useCase.ejecutar(muestraDefectuosa);

        // 3. ASSERT
        assertEquals(4, muestraDefectuosa.getPuntajeTotal(), "El puntaje total debió calcularse en 4");
        assertEquals("Producto no recomendable", muestraDefectuosa.getClasificacion(), "Un puntaje menor a 9 es Producto no recomendable");
    }

    @Test
    void obtenerTodasLasEvaluacionesDebeRetornarElHistorialDelRepositorio() {
        // 1. ARRANGE
        List<EvaluacionSensorial> listaHistorial = new ArrayList<>();
        listaHistorial.add(new EvaluacionSensorial());
        listaHistorial.add(new EvaluacionSensorial());

        class AnalisisRepositorioMock implements AnalisisRepositorio {
            @Override public void guardar(EvaluacionSensorial e) {}
            @Override public List<EvaluacionSensorial> obtenerTodas() { return listaHistorial; }
            @Override public EvaluacionSensorial obtenerUltimoAnalisis() { return null; }

            // 🔒 SOLUCIÓN: Implementamos el método exigido por la interfaz
            @Override public List<EvaluacionSensorial> obtenerTodasPorExperto(String expertoCorreo) { return listaHistorial; }
        }

        RealizarAnalisisUseCase useCase = new RealizarAnalisisUseCase(new AnalisisRepositorioMock());

        // 2. ACT
        List<EvaluacionSensorial> resultado = useCase.obtenerTodasLasEvaluaciones();

        // 3. ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debe retornar el listado íntegro de análisis guardados");
    }
}