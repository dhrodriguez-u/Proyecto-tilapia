package Proyect.infrastructure.repositories;

import Proyect.entities.EvaluacionSensorial;
import Proyect.infrastructure.DatabaseConnector;
import Proyect.usecases.ports.AnalisisRepositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAnalisisRepositorio implements AnalisisRepositorio {

    @Override
    public void guardar(EvaluacionSensorial eval) {
        String sql = "INSERT INTO evaluaciones (lote, cantidad, ojos, branquias, abdomen, olor, total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eval.getCodigoLote());
            stmt.setInt(2, eval.getCantidadTilapias());
            stmt.setInt(3, eval.getOjos());
            stmt.setInt(4, eval.getBranquias());
            stmt.setInt(5, eval.getCavidadAbdominal());
            stmt.setInt(6, eval.getOlor());
            stmt.setInt(7, eval.getPuntajeTotal());

            stmt.executeUpdate();
            System.out.println("Análisis guardado con éxito en MySQL.");

        } catch (SQLException e) {
            System.err.println("Error al guardar el análisis: " + e.getMessage());
        }
    }

    @Override
    public List<EvaluacionSensorial> obtenerTodos() {
        List<EvaluacionSensorial> lista = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Aquí mapeas los resultados de la DB a tu objeto entidad
                EvaluacionSensorial eval = new EvaluacionSensorial(
                        rs.getString("lote"),
                        rs.getInt("cantidad"),
                        rs.getInt("ojos"),
                        rs.getInt("branquias"),
                        rs.getInt("abdomen"),
                        rs.getInt("olor"),
                        "" // Si tienes campo de notas, agrégalo aquí
                );
                lista.add(eval);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las evaluaciones: " + e.getMessage());
        }
        return lista;
    }
}