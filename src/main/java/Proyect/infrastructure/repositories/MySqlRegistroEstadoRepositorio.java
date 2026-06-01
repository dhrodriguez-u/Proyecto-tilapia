package Proyect.infrastructure.repositories;

import Proyect.entities.RegistroEstado;
import Proyect.infrastructure.DatabaseConnector;
import Proyect.usecases.ports.RegistroEstadoRepositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlRegistroEstadoRepositorio implements RegistroEstadoRepositorio {

    @Override
    public void guardar(RegistroEstado reg) {
        // 🔒 CORRECCIÓN: Agregamos 'creador_correo' en las columnas y el octavo '?'
        String sql = "INSERT INTO registro_estado (estacion_nombre, temperatura, ph, oxigeno_disuelto, turbidez, observaciones, alerta, creador_correo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reg.getEstacionNombre());
            stmt.setDouble(2, reg.getTemperatura());
            stmt.setDouble(3, reg.getPh());
            stmt.setDouble(4, reg.getOxigenoDisuelto());
            stmt.setDouble(5, reg.getTurbidez());
            stmt.setString(6, reg.getObservaciones());
            stmt.setString(7, reg.getAlerta());

            // 🔒 CLAVE 1: Guardamos el correo del Piscicultor actual en MySQL
            stmt.setString(8, reg.getCreadorCorreo());

            stmt.executeUpdate();
            System.out.println("Estado de la estación guardado con éxito en MySQL.");

        } catch (SQLException e) {
            System.err.println("Error al guardar estado de estación: " + e.getMessage());
        }
    }

    @Override
    public List<RegistroEstado> obtenerTodos() {
        List<RegistroEstado> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_estado ORDER BY id DESC";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RegistroEstado reg = new RegistroEstado();
                reg.setId(rs.getInt("id"));
                reg.setEstacionNombre(rs.getString("estacion_nombre"));
                reg.setTemperatura(rs.getDouble("temperatura"));
                reg.setPh(rs.getDouble("ph"));
                reg.setOxigenoDisuelto(rs.getDouble("oxigeno_disuelto"));
                reg.setTurbidez(rs.getDouble("turbidez"));
                reg.setObservaciones(rs.getString("observaciones"));
                reg.setAlerta(rs.getString("alerta"));

                Timestamp ts = rs.getTimestamp("fecha_hora_registro");
                if (ts != null) {
                    reg.setFechaHoraRegistro(ts.toString());
                } else {
                    reg.setFechaHoraRegistro("Sin fecha");
                }

                // 🔒 CLAVE 2: Extraemos el correo guardado en la BD y lo subimos al objeto
                reg.setCreadorCorreo(rs.getString("creador_correo"));

                lista.add(reg);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener registros de estaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(RegistroEstado reg) {
        String sql = "UPDATE registro_estado SET estacion_nombre = ?, temperatura = ?, ph = ?, oxigeno_disuelto = ?, turbidez = ?, observaciones = ?, alerta = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reg.getEstacionNombre());
            stmt.setDouble(2, reg.getTemperatura());
            stmt.setDouble(3, reg.getPh());
            stmt.setDouble(4, reg.getOxigenoDisuelto());
            stmt.setDouble(5, reg.getTurbidez());
            stmt.setString(6, reg.getObservaciones());
            stmt.setString(7, reg.getAlerta());
            stmt.setInt(8, reg.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar registro de estación en MySQL: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idRegistro) {
        String sql = "DELETE FROM registro_estado WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRegistro);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar registro de estación en MySQL: " + e.getMessage());
            return false;
        }
    }
}