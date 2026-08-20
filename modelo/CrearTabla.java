package modelo;

import java.sql.Connection;
import java.sql.Statement;

public class CrearTabla {
    public static void main(String[] args) {
        Connection conn = ConexionBD.getInstancia().getConexion();
        String sql = "CREATE TABLE IF NOT EXISTS jugadores (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nombre TEXT NOT NULL," +
                     "puntaje INTEGER NOT NULL" +
                     ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla creada o ya existía");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
