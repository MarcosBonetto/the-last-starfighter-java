package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private final Connection conn;

    public JugadorDAO() {
        conn = ConexionBD.getInstancia().getConexion();
    }

    public void guardarPuntaje(JugadorBD j) {
        String sql = "INSERT INTO jugadores (nombre, puntaje) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getNombre());
            ps.setInt(2, j.getPuntaje());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JugadorBD> top5() {
        List<JugadorBD> lista = new ArrayList<>();
        String sql = "SELECT nombre, puntaje FROM jugadores ORDER BY puntaje DESC LIMIT 5";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new JugadorBD(rs.getString("nombre"), rs.getInt("puntaje")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
