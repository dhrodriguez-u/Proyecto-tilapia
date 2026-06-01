package Proyect.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormaNtc1443Test {

    @Test
    void evaluarPuntajeDebeClasificarLaCalidadSegunLosRangosDeLaNorma() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        NormaNtc1443 norma = new NormaNtc1443();

        // 2. ¿Qué acción voy a ejecutar? (ACT) y ¿Qué debe quedar cierto después? (ASSERT)

        // --- CASO 1: Excelente Calidad (Total >= 17) ---
        String resultadoExcelente = norma.evaluarPuntaje(18);
        assertEquals("EXCELENTE CALIDAD (Apto consumo y exportación)", resultadoExcelente,
                "Un puntaje de 18 debe clasificar como Excelente Calidad");

        // --- CASO 2: Buena Calidad (13 <= Total < 17) ---
        String resultadoBueno = norma.evaluarPuntaje(14);
        assertEquals("BUENA CALIDAD (Apto consumo nacional)", resultadoBueno,
                "Un puntaje de 14 debe clasificar como Buena Calidad");

        // --- CASO 3: Calidad Aceptable (9 <= Total < 13) ---
        String resultadoAceptable = norma.evaluarPuntaje(10);
        assertEquals("CALIDAD ACEPTABLE (Procesamiento industrial)", resultadoAceptable,
                "Un puntaje de 10 debe clasificar como Calidad Aceptable");

        // --- CASO 4: Rechazo (Total < 9) ---
        String resultadoRechazo = norma.evaluarPuntaje(5);
        assertEquals("RECHAZO / NO RECOMENDABLE (Riesgo Sanitario)", resultadoRechazo,
                "Un puntaje de 5 debe generar un veredicto de Rechazo");
    }

    @Test
    void verificarMetadatosYTextoDebeMantenerConstantesInmutables() {
        // 1. ARRANGE
        NormaNtc1443 norma = new NormaNtc1443();

        // 2. ACT
        String nombreObtenido = norma.getNombreNorma();
        String urlObtenida = norma.getUrlVideoHaccp();
        String textoDetallado = norma.getTextoDetallado();

        // 3. ASSERT
        assertEquals("NTC 1443 - Productos de la Pesca: Tilapia Fresca", nombreObtenido,
                "El nombre oficial de la norma inmutable debe coincidir");

        assertEquals("https://www.youtube.com/watch?v=Z8ARhip2a8o", urlObtenida,
                "La URL técnica del video HACCP debe ser la correcta");

        assertNotNull(textoDetallado, "El manual técnico detallado no puede ser nulo o vacío");
        assertTrue(textoDetallado.contains("SISTEMA OCULAR"), "El texto debe incluir los parámetros del órgano diana");
    }
}