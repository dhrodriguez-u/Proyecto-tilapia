package Proyect.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void verificarCreacionDeUsuarioDebeMantenerConsistenciaEnCredenciales() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        // Precondiciones: Definimos los parámetros ideales para dar de alta una cuenta
        String nombrePrueba = "Ing. Carlos Restrepo";
        String correoPrueba = "carlos.experto@piscicola.com";
        String passwordPrueba = "TilapiaSegura2026*";
        String rolPrueba = "EXPERTO";

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // Ejecución: Invocamos tu constructor con parámetros completo
        Usuario usuario = new Usuario(nombrePrueba, correoPrueba, passwordPrueba, rolPrueba);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // Postcondiciones: Validamos la integridad del estado del objeto mediante sus getters reales
        assertEquals("Ing. Carlos Restrepo", usuario.getNombre(), "El nombre completo del usuario debe registrarse intacto");
        assertEquals("carlos.experto@piscicola.com", usuario.getCorreo(), "El correo electrónico debe guardarse correctamente");
        assertEquals("TilapiaSegura2026*", usuario.getPassword(), "La contraseña de seguridad no debe alterarse");
        assertEquals("EXPERTO", usuario.getRol(), "El rol asignado en el sistema debe coincidir");
    }

    @Test
    void verificarModificacionDeRolDebeCambiarPermisosExitosamente() {
        // 1. ARRANGE
        // Precondición: Creamos un usuario inicialmente con un rol básico
        Usuario usuario = new Usuario("Marta Gómez", "marta@piscicola.com", "Clave123", "OPERARIO");

        // 2. ACT
        // Ejecución: Probamos tu método setter de actualización
        usuario.setRol("ADMINISTRADOR");

        // 3. ASSERT
        // Postcondición: Validamos que el nuevo rol se haya sobreescrito de forma exitosa
        assertEquals("ADMINISTRADOR", usuario.getRol(), "El setter debe actualizar de forma efectiva el rol del usuario");
    }
}