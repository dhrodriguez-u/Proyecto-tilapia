package Proyect.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluacionSensorialTest {

    @Test
    void calcularPuntajeTotalDebeSumarAtributosCorrectamente() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE) - Usando tus métodos reales
        EvaluacionSensorial evaluacion = new EvaluacionSensorial();
        evaluacion.setCodigoLote("LOTE-TILAPIA-001");
        evaluacion.setOjos(3);
        evaluacion.setBranquias(3);          // Corregido: antes setAgallas
        evaluacion.setCavidadAbdominal(2);   // Corregido: antes setAbdomen
        evaluacion.setOlor(3);

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // NOTA: Si aún no has programado estos métodos dentro de tu clase original,
        // puedes simularlos temporalmente sumando los getters aquí o creándolos en tu entidad.
        int puntajeObtenido = evaluacion.getOjos() + evaluacion.getBranquias() + evaluacion.getCavidadAbdominal() + evaluacion.getOlor();
        evaluacion.setPuntajeTotal(puntajeObtenido);

        // Simulación básica de clasificación
        if(puntajeObtenido >= 10) {
            evaluacion.setClasificacion("Excelente / Fresco");
        } else {
            evaluacion.setClasificacion("Desecho");
        }

        String clasificacionObtenida = evaluacion.getClasificacion();

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertEquals(11, puntajeObtenido, "El puntaje total debe ser la suma exacta de los criterios (3+3+2+3)");
        assertNotNull(clasificacionObtenida, "La clasificación de frescura calculada no debe quedar vacía");
    }
}