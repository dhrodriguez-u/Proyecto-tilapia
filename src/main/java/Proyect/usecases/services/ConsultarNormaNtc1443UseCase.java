package Proyect.usecases.services;

import Proyect.entities.NormaNtc1443;
import Proyect.entities.AceptacionNorma;
import Proyect.entities.Usuario;

public class ConsultarNormaNtc1443UseCase {

    private final NormaNtc1443 normaDefinida;

    public ConsultarNormaNtc1443UseCase() {
        // Inicializa la entidad inmutable del dominio
        this.normaDefinida = new NormaNtc1443();
    }

    /**
     * Entrega los datos puros de la norma técnica para renderizarse en pantalla.
     */
    public NormaNtc1443 consultarDetallesNorma() {
        return this.normaDefinida;
    }

    /**
     * Procesa la firma del acuerdo legal-técnico cuando el experto acepta la normativa.
     * @return Entidad AceptacionNorma con la estampa de tiempo capturada.
     */
    public AceptacionNorma registrarAceptacionDeNorma(Usuario usuario, boolean acepto) {
        if (usuario == null) {
            throw new IllegalArgumentException("Error Crítico: No se puede registrar la firma técnica sin un usuario autenticado en la sesión activa.");
        }

        // Instancia la entidad que documenta formalmente la auditoría del Experto
        AceptacionNorma registro = new AceptacionNorma(
                usuario.getCorreo(),
                normaDefinida.getNombreNorma(),
                acepto
        );

        // Registro de traza en la terminal interna de la arquitectura
        System.out.println("LOG AUDITORÍA CORE: El experto [" + registro.getCorreoUsuario() +
                "] ha validado legalmente los criterios de la norma en: " + registro.getFechaHoraAceptacion());

        return registro;
    }
}