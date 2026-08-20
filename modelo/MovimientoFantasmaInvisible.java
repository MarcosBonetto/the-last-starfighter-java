package modelo;

public class MovimientoFantasmaInvisible implements MovimientoEnemigo {

    @Override
    public void mover(personaje_base enemigo) {
        // cuando está invisible se mueve más lento
        int dx = -enemigo.getSpeed();
        enemigo.setX(enemigo.getX() + dx);
    }
}
