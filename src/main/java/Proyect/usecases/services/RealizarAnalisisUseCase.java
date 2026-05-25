package Proyect.usecases.services;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;

public class RealizarAnalisisUseCase {

    private final AnalisisRepositorio repositorio;

    public RealizarAnalisisUseCase(
            AnalisisRepositorio repositorio
    ) {

        this.repositorio = repositorio;
    }

    public void ejecutar(
            EvaluacionSensorial evaluacion
    ) {

        int total =
                evaluacion.getPuntajeTotal();

        if(total >= 17){

            evaluacion.setClasificacion(
                    "Excelente calidad"
            );

        } else if(total >= 13){

            evaluacion.setClasificacion(
                    "Buena calidad"
            );

        } else if(total >= 9){

            evaluacion.setClasificacion(
                    "Calidad aceptable"
            );

        } else {

            evaluacion.setClasificacion(
                    "Producto no recomendable"
            );
        }

        repositorio.guardar(evaluacion);
    }
}