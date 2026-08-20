package modelo;

public class MovimientoFantasmaVisible implements MovimientoEnemigo {

    @Override
    public void mover(personaje_base enemigo) {
        // cuando el fantasma está visible se mueve mas rapido hacia la izquierda
        int dx = -enemigo.getSpeed() * 2;
        enemigo.setX(enemigo.getX() + dx);
    }
}
