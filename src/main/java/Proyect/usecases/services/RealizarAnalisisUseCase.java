package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.entities.Usuario;
import Proyect.usecases.ports.AnalisisRepositorio;
import java.util.List;

public class RealizarAnalisisUseCase {

    private final AnalisisRepositorio repositorio;

    public RealizarAnalisisUseCase(AnalisisRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Versión compatible con el flujo de auditoría y tracking de usuarios.
     * Asigna el autor de la evaluación antes de guardarla.
     */
    public void ejecutar(EvaluacionSensorial evaluacion, Usuario expertoLogueado) {
        if (expertoLogueado != null) {
            evaluacion.setCreadorCorreo(expertoLogueado.getCorreo());
        }
        // Deriva el flujo al cálculo estándar de la norma
        ejecutar(evaluacion);
    }

    public void ejecutar(EvaluacionSensorial evaluacion) {
        // 1. Calculamos y aseguramos el puntaje total sumando las notas organolépticas
        int total = evaluacion.getOjos() + evaluacion.getBranquias() + evaluacion.getCavidadAbdominal() + evaluacion.getOlor();
        evaluacion.setPuntajeTotal(total);

        // 2. Evaluamos la escala de calidad según la norma NTC 1443
        if (total >= 17) {
            evaluacion.setClasificacion("Excelente calidad");
        } else if (total >= 13) {
            evaluacion.setClasificacion("Buena calidad");
        } else if (total >= 9) {
            evaluacion.setClasificacion("Calidad aceptable");
        } else {
            evaluacion.setClasificacion("Producto no recomendable");
        }

        // 3. Persistencia directa en MySQL
        repositorio.guardar(evaluacion);
    }

    /**
     * MÉTODO ADICIONAL INTEGRADO
     * Actúa como puente para recuperar todas las evaluaciones almacenadas desde la base de datos
     * y permite alimentar la tabla del módulo de Reportes.
     */
    public List<EvaluacionSensorial> obtenerTodasLasEvaluaciones() {
        return repositorio.obtenerTodas();
    }
}