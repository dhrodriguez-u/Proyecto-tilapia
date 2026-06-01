package Proyect.infrastructure.repositories;

import Proyect.entities.EvaluacionSensorial;
import Proyect.infrastructure.DatabaseConnector;
import Proyect.usecases.ports.ReporteRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlReporteRepository implements ReporteRepository {

    @Override
    public List<EvaluacionSensorial> obtenerTodosLosAnalisis() {
        List<EvaluacionSensorial> lista = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EvaluacionSensorial ev = new EvaluacionSensorial();
                ev.setCodigoLote(rs.getString("codigo_lote"));
                ev.setCantidadTilapias(rs.getInt("cantidad_tilapias"));
                ev.setOjos(rs.getInt("ojos"));
                ev.setBranquias(rs.getInt("branquias"));
                ev.setCavidadAbdominal(rs.getInt("cavidad_abdominal"));
                ev.setOlor(rs.getInt("olor"));
                ev.setPuntajeTotal(rs.getInt("total"));
                ev.setClasificacion(rs.getString("clasificacion"));

                if (rs.getDate("fecha_evaluacion") != null) {
                    ev.setFechaEvaluacion(rs.getDate("fecha_evaluacion").toString());
                } else {
                    ev.setFechaEvaluacion("Sin Fecha");
                }

                lista.add(ev);
            }
        } catch (SQLException e) {
            System.err.println("Error en SqlReporteRepository: " + e.getMessage());
        }
        return lista;
    }
}