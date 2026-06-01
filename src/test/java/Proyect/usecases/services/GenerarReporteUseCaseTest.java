package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.ReporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GenerarReporteUseCaseTest {

    private final ReporteRepository reporteRepositoryMock = new ReporteRepository() {
        @Override
        public List<EvaluacionSensorial> obtenerTodosLosAnalisis() {
            return null;
        }
    };

    @Test
    void exportarATextoPlanoDebeGenerarElArchivoYRetornarTrueCuandoHayDatos(@TempDir Path tempDir) {
        // 1. ARRANGE
        GenerarReporteUseCase useCase = new GenerarReporteUseCase(reporteRepositoryMock);
        File archivoDestino = tempDir.resolve("reporte_test.txt").toFile();

        List<EvaluacionSensorial> listaMuestra = new ArrayList<>();
        EvaluacionSensorial eval = new EvaluacionSensorial();
        eval.setCodigoLote("LOTE-EXPORT-99");
        eval.setCantidadTilapias(20);
        eval.setPuntajeTotal(18);
        eval.setClasificacion("EXCELENTE CALIDAD");
        eval.setObservaciones("Lote apto para envío internacional");
        listaMuestra.add(eval);

        // 2. ACT
        // 🔒 AJUSTE: Pasamos los 4 argumentos requeridos por la nueva firma (ej: lista, archivo, totalLotes, indiceAprobacion)
        boolean resultadoExportacion = useCase.exportarATextoPlano(listaMuestra, archivoDestino, "3 Lotes", "66,7%");

        // 3. ASSERT
        assertTrue(resultadoExportacion, "El caso de uso debe retornar true confirmando el éxito del guardado");
        assertTrue(archivoDestino.exists(), "El archivo físico .txt debió haberse creado en el almacenamiento");
        assertTrue(archivoDestino.length() > 0, "El reporte generado debe contener los caracteres de la cabecera e información");
    }

    @Test
    void exportarATextoPlanoDebeRetornarFalseSiLaListaDeMuestrasEstaVacia(@TempDir Path tempDir) {
        // 1. ARRANGE
        GenerarReporteUseCase useCase = new GenerarReporteUseCase(reporteRepositoryMock);
        File archivoDestino = tempDir.resolve("reporte_vacio.txt").toFile();

        List<EvaluacionSensorial> listaVacia = new ArrayList<>();

        // 2. ACT
        // 🔒 AJUSTE: Rellenamos con los 4 argumentos correspondientes para cumplir el contrato del método
        boolean resultadoConListaVacia = useCase.exportarATextoPlano(listaVacia, archivoDestino, "0 Lotes", "0,0%");
        boolean resultadoConListaNull = useCase.exportarATextoPlano(null, archivoDestino, "0 Lotes", "0,0%");

        // 3. ASSERT
        assertFalse(resultadoConListaVacia, "Debe rechazar la operación y retornar false si la lista no contiene elementos");
        assertFalse(resultadoConListaNull, "Debe rechazar la operación y retornar false ante parámetros nulos");
        assertFalse(archivoDestino.exists(), "No debió crearse ningún archivo en disco al fallar las precondiciones");
    }
}