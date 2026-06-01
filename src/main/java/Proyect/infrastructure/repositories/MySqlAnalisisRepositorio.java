package Proyect.infrastructure.repositories;

import Proyect.entities.EvaluacionSensorial;
import Proyect.usecases.ports.AnalisisRepositorio;
import Proyect.infrastructure.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlAnalisisRepositorio implements AnalisisRepositorio {

    @Override
    public void guardar(EvaluacionSensorial evaluacion) {
        // ACTUALIZADO: Añadimos la columna creador_correo al INSERT
        String sql = "INSERT INTO evaluaciones (codigo_lote, cantidad_tilapias, ojos, branquias, cavidad_abdominal, olor, total, clasificacion, fecha_evaluacion, observaciones, creador_correo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Estampa de tiempo exacta del sistema al concluir el análisis
        LocalDateTime horaActual = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraString = horaActual.format(formateador);

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, evaluacion.getCodigoLote());
            pstmt.setInt(2, evaluacion.getCantidadTilapias());
            pstmt.setInt(3, evaluacion.getOjos());
            pstmt.setInt(4, evaluacion.getBranquias());
            pstmt.setInt(5, evaluacion.getCavidadAbdominal());
            pstmt.setInt(6, evaluacion.getOlor());
            pstmt.setInt(7, evaluacion.getPuntajeTotal());
            pstmt.setString(8, evaluacion.getClasificacion());

            // Forzamos la inserción de fecha y hora del evento
            pstmt.setString(9, fechaHoraString);

            String obs = evaluacion.getObservaciones();
            pstmt.setString(10, (obs == null || obs.trim().isEmpty()) ? "Sin observaciones técnicas." : obs);

            // NUEVO PARAMETRO: Guardamos el correo del experto en la BD
            pstmt.setString(11, evaluacion.getCreadorCorreo());

            pstmt.executeUpdate();
            System.out.println("💾 [OK] Análisis guardado exitosamente con fecha: " + fechaHoraString);

        } catch (SQLException e) {
            System.err.println("🚨 ERROR AL GUARDAR EN MYSQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<EvaluacionSensorial> obtenerTodas() {
        List<EvaluacionSensorial> lista = new ArrayList<>();
        // ACTUALIZADO: Seleccionamos también la columna creador_correo
        String sql = "SELECT codigo_lote, cantidad_tilapias, ojos, branquias, cavidad_abdominal, olor, total, clasificacion, fecha_evaluacion, observaciones, creador_correo FROM evaluaciones";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EvaluacionSensorial eval = new EvaluacionSensorial();

                eval.setCodigoLote(rs.getString("codigo_lote"));
                eval.setCantidadTilapias(rs.getInt("cantidad_tilapias"));
                eval.setOjos(rs.getInt("ojos"));
                eval.setBranquias(rs.getInt("branquias"));
                eval.setCavidadAbdominal(rs.getInt("cavidad_abdominal"));
                eval.setOlor(rs.getInt("olor"));
                eval.setPuntajeTotal(rs.getInt("total"));
                eval.setClasificacion(rs.getString("clasificacion"));
                eval.setFechaEvaluacion(rs.getString("fecha_evaluacion"));

                String obs = rs.getString("observaciones");
                eval.setObservaciones((obs == null) ? "Inspección registrada." : obs);

                // NUEVO MAPEO: Recuperamos el correo del creador
                eval.setCreadorCorreo(rs.getString("creador_correo"));

                lista.add(eval);
            }

        } catch (SQLException e) {
            System.err.println("🚨 ERROR CRÍTICO AL LEER DESDE MYSQL: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public EvaluacionSensorial obtenerUltimoAnalisis() {
        return null;
    }

    /**
     * SOLUCIÓN AL ERROR: Implementación del método abstracto filtrando directamente en la consulta SQL.
     * Esto asegura que la base de datos solo devuelva los registros del Experto en sesión.
     */
    @Override
    public List<EvaluacionSensorial> obtenerTodasPorExperto(String correoExperto) {
        List<EvaluacionSensorial> lista = new ArrayList<>();
        String sql = "SELECT codigo_lote, cantidad_tilapias, ojos, branquias, cavidad_abdominal, olor, total, clasificacion, fecha_evaluacion, observaciones, creador_correo FROM evaluaciones WHERE creador_correo = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correoExperto);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EvaluacionSensorial eval = new EvaluacionSensorial();

                    eval.setCodigoLote(rs.getString("codigo_lote"));
                    eval.setCantidadTilapias(rs.getInt("cantidad_tilapias"));
                    eval.setOjos(rs.getInt("ojos"));
                    eval.setBranquias(rs.getInt("branquias"));
                    eval.setCavidadAbdominal(rs.getInt("cavidad_abdominal"));
                    eval.setOlor(rs.getInt("olor"));
                    eval.setPuntajeTotal(rs.getInt("total"));
                    eval.setClasificacion(rs.getString("clasificacion"));
                    eval.setFechaEvaluacion(rs.getString("fecha_evaluacion"));

                    String obs = rs.getString("observaciones");
                    eval.setObservaciones((obs == null) ? "Inspección registrada." : obs);

                    eval.setCreadorCorreo(rs.getString("creador_correo"));

                    lista.add(eval);
                }
            }

        } catch (SQLException e) {
            System.err.println("🚨 ERROR CRÍTICO AL FILTRAR POR EXPERTO EN MYSQL: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}