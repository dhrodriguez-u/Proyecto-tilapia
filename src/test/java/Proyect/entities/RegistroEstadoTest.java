package Proyect.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistroEstadoTest {

    @Test
    void verificarRegistroDeEstadoDebeMantenerConsistenciaDeVariablesHidricas() {
        // 1. ¿Qué debe estar listo antes? (ARRANGE)
        // Precondiciones: Definimos las métricas reales que se capturan de un estanque
        int idPrueba = 45;
        String nombreEstacion = "Estanque de Engorde N° 3";
        double tempPrueba = 27.5;              // Temperatura óptima para tilapia colombiana
        double phPrueba = 7.2;                 // pH neutro saludable
        double oxigenoPrueba = 5.8;            // Oxígeno disuelto estable (mg/L)
        double turbidezPrueba = 30.2;          // Unidades de turbidez (NTU)
        String observacionesPrueba = "Muestreo ordinario matutino sin novedades";
        String alertaPrueba = "ESTABLE / SIN ALERTA";
        String fechaHoraPrueba = "2026-05-31 08:30:00";

        // 2. ¿Qué acción voy a ejecutar? (ACT)
        // Ejecución: Instanciamos el objeto vacío y usamos tus setters originales
        RegistroEstado registro = new RegistroEstado();
        registro.setId(idPrueba);
        registro.setEstacionNombre(nombreEstacion);
        registro.setTemperatura(tempPrueba);
        registro.setPh(phPrueba);
        registro.setOxigenoDisuelto(oxigenoPrueba);
        registro.setTurbidez(turbidezPrueba);
        registro.setObservaciones(observacionesPrueba);
        registro.setAlerta(alertaPrueba);
        registro.setFechaHoraRegistro(fechaHoraPrueba);

        // 3. ¿Qué debe quedar cierto después? (ASSERT)
        // Postcondiciones: Validamos que los getters devuelvan los mismos datos sin alteraciones
        assertEquals(45, registro.getId(), "El identificador correlativo del registro debe coincidir");
        assertEquals("Estanque de Engorde N° 3", registro.getEstacionNombre(), "El nombre de la estación debe ser idéntico");

        // Al validar números decimales (doubles) en JUnit, se recomienda usar un margen de tolerancia insignificante (ej: 0.001)
        assertEquals(27.5, registro.getTemperatura(), 0.001, "La temperatura guardada debe coincidir");
        assertEquals(7.2, registro.getPh(), 0.001, "El nivel de pH debe ser el mismo");
        assertEquals(5.8, registro.getOxigenoDisuelto(), 0.001, "El oxígeno disuelto no debe alterarse");
        assertEquals(30.2, registro.getTurbidez(), 0.001, "La medición de turbidez del agua debe mantenerse");

        assertEquals("Muestreo ordinario matutino sin novedades", registro.getObservaciones(), "Las notas del operario deben ser consistentes");
        assertEquals("ESTABLE / SIN ALERTA", registro.getAlerta(), "El estado de la alerta debe coincidir");
        assertEquals("2026-05-31 08:30:00", registro.getFechaHoraRegistro(), "La estampa de fecha y hora guardada debe ser exacta");
    }
}