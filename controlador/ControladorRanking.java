package controlador;

import modelo.JugadorBD;
import modelo.JugadorDAO;
import java.util.List;

public class ControladorRanking {

    private final JugadorDAO dao = new JugadorDAO();

    public void guardarPuntaje(String nombre, int puntaje) {
        JugadorBD jugador = new JugadorBD(nombre, puntaje);
        dao.guardarPuntaje(jugador);
    }

    public List<JugadorBD> obtenerTop5() {
        return dao.top5();
    }
}
