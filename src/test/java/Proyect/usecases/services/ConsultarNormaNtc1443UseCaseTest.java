package Proyect.usecases.services;

import Proyect.entities.AceptacionNorma;
import Proyect.entities.NormaNtc1443;
import Proyect.entities.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsultarNormaNtc1443UseCaseTest {

    @Test
    void consultarDetallesNormaDebeRetornarEntidadInmutableConDatos() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        ConsultarNormaNtc1443UseCase useCase = new ConsultarNormaNtc1443UseCase();

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        NormaNtc1443 resultado = useCase.consultarDetallesNorma();

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertNotNull(resultado, "La norma consultada no debe ser nula");
        assertEquals("NTC 1443 - Productos de la Pesca: Tilapia Fresca", resultado.getNombreNorma(),
                "El nombre detallado dentro de la entidad debe ser el oficial");
    }

    @Test
    void registrarAceptacionDeNormaDebeRetornarRegistroDeAuditoriaCorrecto() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        ConsultarNormaNtc1443UseCase useCase = new ConsultarNormaNtc1443UseCase();

        // Usamos tu constructor real de Usuario que vimos anteriormente
        Usuario usuarioExperto = new Usuario("Ing. Carlos", "carlos@piscicola.com", "Clave123", "EXPERTO");
        boolean aceptoTerminos = true;

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        AceptacionNorma registroResultado = useCase.registrarAceptacionDeNorma(usuarioExperto, aceptoTerminos);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        assertNotNull(registroResultado, "El objeto de auditoría retornado no puede ser nulo");
        assertEquals("carlos@piscicola.com", registroResultado.getCorreoUsuario(), "Debe mapear el correo del usuario");
        assertEquals("NTC 1443 - Productos de la Pesca: Tilapia Fresca", registroResultado.getNombreNorma(), "Debe heredar el nombre de la norma definida");
        assertTrue(registroResultado.isTerminosAceptados(), "La bandera de aceptación debe quedar en true");
        assertNotNull(registroResultado.getFechaHoraAceptacion(), "Debe contener la estampa de tiempo automática");
    }

    @Test
    void registrarAceptacionDeNormaDebeLanzarExcepcionSiUsuarioEsNull() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        ConsultarNormaNtc1443UseCase useCase = new ConsultarNormaNtc1443UseCase();

        // 2. ¿Qué acción voy a ejecutar? (ACT) y ¿Qué debe quedar cierto después? (ASSERT)
        // Verificamos que al pasar un usuario null, el sistema dispare la excepción controlada de tu 'if'
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            useCase.registrarAceptacionDeNorma(null, true);
        }, "Debe arrojar un IllegalArgumentException ante un usuario inexistente");

        // Validamos que el mensaje de error de la excepción sea exactamente el que escribiste en tu código
        assertEquals("Error Crítico: No se puede registrar la firma técnica sin un usuario autenticado en la sesión activa.",
                excepcion.getMessage());
    }
}