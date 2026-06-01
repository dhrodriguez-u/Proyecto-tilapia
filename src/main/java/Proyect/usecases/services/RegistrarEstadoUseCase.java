package Proyect.usecases.services;

import Proyect.entities.RegistroEstado;
import Proyect.entities.Usuario;
import Proyect.usecases.ports.RegistroEstadoRepositorio;

public class RegistrarEstadoUseCase {

    private final RegistroEstadoRepositorio repositorio;

    public RegistrarEstadoUseCase(RegistroEstadoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Versión adaptada para el flujo de seguridad del Piscicultor.
     */
    public String ejecutar(RegistroEstado registro, Usuario piscicultorLogueado) {
        if (piscicultorLogueado != null) {
            registro.setCreadorCorreo(piscicultorLogueado.getCorreo());
        }
        return ejecutar(registro);
    }

    public String ejecutar(RegistroEstado registro) {
        StringBuilder alertas = new StringBuilder();

        // Validación de rangos óptimos según el diseño lógico de tu compañero
        if (registro.getTemperatura() < 24.0 || registro.getTemperatura() > 32.0) {
            alertas.append("[Temperatura fuera de rango (Óptimo: 24-32°C)] ");
        }
        if (registro.getPh() < 6.5 || registro.getPh() > 8.5) {
            alertas.append("[pH crítico (Óptimo: 6.5-8.5)] ");
        }
        if (registro.getOxigenoDisuelto() < 5.0) {
            alertas.append("[Oxígeno Disuelto bajo (Óptimo: >= 5 mg/L)] ");
        }

        // Si hay alertas, las guardamos en el objeto, si no, queda en Óptimo
        if (alertas.length() > 0) {
            registro.setAlerta(alertas.toString());
        } else {
            registro.setAlerta("Parámetros Óptimos");
        }

        // Guardar en la base de datos
        repositorio.guardar(registro);

        return registro.getAlerta();
    }
}
