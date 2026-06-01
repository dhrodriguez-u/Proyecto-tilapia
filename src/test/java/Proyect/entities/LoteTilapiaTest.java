package Proyect.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoteTilapiaTest {

    @Test
    void verificarCreacionDeLoteDebeMantenerConsistenciaDeDatosPiscicolas() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        // Precondiciones: Definimos los parámetros reales que describen el lote sembrado
        String idPrueba = "LT-2026-X";
        String procedenciaPrueba = "Estanque Central - Meta";
        String tipoPrueba = "Tilapia Roja";
        String condicionPrueba = "Refrigerado";
        String coordenadasPrueba = "4.1420N, 73.6269W";
        String altitudPrueba = "467 msnm";
        int cantidadPrueba = 20;

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // Ejecución: Instanciamos usando tu constructor con parámetros reales
        LoteTilapia lote = new LoteTilapia(
                idPrueba,
                procedenciaPrueba,
                tipoPrueba,
                condicionPrueba,
                coordenadasPrueba,
                altitudPrueba,
                cantidadPrueba
        );

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // Postcondición: Validamos que cada getter retorne exactamente lo inyectado en el constructor
        assertEquals("LT-2026-X", lote.getIdLote(), "El identificador del lote debe registrarse correctamente");
        assertEquals("Estanque Central - Meta", lote.getProcedencia(), "La procedencia geográfica debe coincidir");
        assertEquals("Tilapia Roja", lote.getTipoTilapia(), "El tipo o variedad de pescado debe mantenerse");
        assertEquals("Refrigerado", lote.getCondicion(), "La condición de conservación térmica debe ser idéntica");
        assertEquals("4.1420N, 73.6269W", lote.getCoordenadas(), "Las coordenadas deben mapearse sin alteraciones");
        assertEquals("467 msnm", lote.getAltitud(), "La altitud registrada debe ser coherente");

        // Verificación de integridad numérica crítica para el laboratorio
        assertEquals(20, lote.getCantidadIndividuos(), "El tamaño de la muestra debe ser exactamente 20");
        assertTrue(lote.getCantidadIndividuos() > 0, "El tamaño de la muestra debe ser un número entero positivo");
    }
}
