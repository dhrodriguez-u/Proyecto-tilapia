package Proyect.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AceptacionNormaTest {

    @Test
    void verificarRegistroDeAceptacionDebeGuardarDatosYFechaCorrectamente() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        // Precondición: Definimos las credenciales del usuario y la norma que va a aceptar
        String correoPrueba = "experto.calidad@piscicola.com";
        String normaPrueba = "NTC 1443 - Frescura de la Tilapia";
        boolean aceptoTerminos = true;

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // Ejecución: Instanciamos usando tu constructor con parámetros, el cual dispara el LocalDateTime.now() automático
        AceptacionNorma registro = new AceptacionNorma(correoPrueba, normaPrueba, aceptoTerminos);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // Postcondición: Comprobamos que el objeto retenga la información intacta y que la marca de tiempo no sea nula
        assertEquals("experto.calidad@piscicola.com", registro.getCorreoUsuario(), "El correo guardado debe coincidir con el de la precondición");
        assertEquals("NTC 1443 - Frescura de la Tilapia", registro.getNombreNorma(), "El nombre de la norma debe ser idéntico");
        assertTrue(registro.isTerminosAceptados(), "El estado de los términos aceptados debe quedar en verdadero (true)");

        assertNotNull(registro.getFechaHoraAceptacion(), "El sistema automático debió asignar la fecha y hora actual");
        assertTrue(registro.getFechaHoraAceptacion().isBefore(LocalDateTime.now().plusSeconds(1)), "La marca de tiempo debe ser coherente con el momento de ejecución");
    }
}
