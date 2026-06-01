package Proyect.entities;

public class NormaNtc1443 {
    private final String nombreNorma = "NTC 1443 - Productos de la Pesca: Tilapia Fresca";

    // CORRECCIÓN: URL técnica real que abrirá el navegador a través de HostServices
    private final String urlVideoHaccp = "https://www.youtube.com/watch?v=Z8ARhip2a8o";

    public NormaNtc1443() {}

    public String getNombreNorma() { return nombreNorma; }
    public String getUrlVideoHaccp() { return urlVideoHaccp; }

    /**
     * Regla de negocio inmutable: Dictamina la calidad según el puntaje total
     */
    public String evaluarPuntaje(int total) {
        if (total >= 17) return "EXCELENTE CALIDAD (Apto consumo y exportación)";
        else if (total >= 13) return "BUENA CALIDAD (Apto consumo nacional)";
        else if (total >= 9) return "CALIDAD ACEPTABLE (Procesamiento industrial)";
        else return "RECHAZO / NO RECOMENDABLE (Riesgo Sanitario)";
    }

    public String getTextoDetallado() {
        return "=================================================================================\n" +
                "       NORMA TÉCNICA COLOMBIANA NTC 1443 - REQUISITOS PARA PRODUCTOS DE LA PESCA \n" +
                "=================================================================================\n" +
                "MANUAL DE PROTOCOLO TÉCNICO PARA EXPERTOS EVALUADORES E INSPECCIÓN SENSORIAL\n\n" +

                "I. OBJETO Y ALCANCE\n" +
                "Esta normativa específica los parámetros organolépticos, microbiológicos y de inocuidad\n" +
                "que debe cumplir la Tilapia Fresca (Oreochromis niloticus) en los eslabones de cosecha,\n" +
                "transporte y recepción en plantas de procesamiento industrial.\n\n" +

                "II. TABLA DE EVALUACIÓN DE ÓRGANOS DIANA (Puntaje Máximo: 20 Puntos)\n" +
                "---------------------------------------------------------------------------------\n" +
                "1. SISTEMA OCULAR Y ESCLERÓTICA (Máx: 5 pts)\n" +
                "   - [5 Puntos] Excelente: Ojos convexos, córnea completamente translúcida y limpia,\n" +
                "     pupila negra brillante con reflejo cristalino nítido.\n" +
                "   - [3 Puntos] Aceptable: Ojos planos, córnea sutilmente opalescente, pérdida parcial\n" +
                "     del brillo de la pupila.\n" +
                "   - [0 Puntos] Rechazo: Ojos hundidos (cóncavos), córnea lechosa o gris opaca,\n" +
                "     derrame hemorrágico interno visible.\n\n" +

                "2. ARCOS BRANQUIALES Y MUCOSIDAD (Máx: 5 pts)\n" +
                "   - [5 Puntos] Excelente: Color rojo sangre vivo, brillante y homogéneo. Laminillas\n" +
                "     perfectamente separadas, libres de filamentos adheridos. Mucosidad escasa y transparente.\n" +
                "   - [3 Puntos] Aceptable: Color rosa pálido o con tonalidades marrón claro. Presencia\n" +
                "     de mucosidad translúcida levemente densa.\n" +
                "   - [0 Puntos] Rechazo: Color grisáceo, amarillento o marrón oscuro en estado de lisis.\n" +
                "     Mucosidad densa, opaca, aglutinada y con presencia de detritos.\n\n" +

                "3. TEJIDO MUSCULAR, ABDOMEN Y PIEL (Máx: 5 pts)\n" +
                "   - [5 Puntos] Excelente: Piel con pigmentación brillante, escamas fuertemente\n" +
                "     adheridas. Carne elástica (al ejercer presión digital, recupera su forma de inmediato).\n" +
                "     Peritoneo abdominal íntegro, fuertemente adherido a las paredes costales.\n" +
                "   - [3 Puntos] Aceptable: Ligera pérdida de la brillantez cutánea, pérdida parcial de\n" +
                "     la elasticidad (la huella digital tarda unos segundos en desaparecer).\n" +
                "   - [0 Puntos] Rechazo: Flacidez extrema, desprendimiento espontáneo de escamas.\n" +
                "     Pared abdominal blanda, rota o con manchas de autólisis biliar severa.\n\n" +

                "4. PERFIL OLFATIVO INTEGRAL (Máx: 5 pts)\n" +
                "   - [5 Puntos] Excelente: Olor limpio y fresco, característico de agua dulce continental\n" +
                "     o algas verdes sanas.\n" +
                "   - [2 Puntos] Aceptable: Olor neutro, pérdida de frescura, notas tenues a humedad,\n" +
                "     barro amargo o pasto cortado.\n" +
                "   - [0 Puntos] Rechazo: Olor amoniacal punzante, sulfhídrico (huevo podrido), rancio,\n" +
                "     o notas de descomposición orgánica avanzada.\n\n" +

                "III. CRITERIOS DE RECHAZO INMEDIATO (PUNTOS CRÍTICOS DE CONTROL - PCC)\n" +
                "El lote completo será decomisado de forma inmediata si se detecta:\n" +
                "1. Presencia de parásitos activos en el tejido muscular o cavidad visceral.\n" +
                "2. Temperatura interna de la carne superior a los 4.0 °C en el momento de recepción.\n" +
                "3. Contaminación evidente por hidrocarburos, lodos químicos o sustancias extrañas.\n\n" +

                "IV. ESTÁNDARES MICROBIOLÓGICOS COMPLEMENTARIOS\n" +
                "- Recuento de microorganismos mesófilos: < 1x10^5 UFC/g\n" +
                "- Escherichia coli: Ausencia en 25g\n" +
                "- Salmonella spp.: Ausencia estricta en 25g\n\n" +
                "---------------------------------------------------------------------------------\n" +
                "NOTA: Cualquier alteración sospechosa obliga al Experto Evaluador a suspender el\n" +
                "muestreo ordinario y remitir las muestras al laboratorio de análisis instrumental.";
    }
}
