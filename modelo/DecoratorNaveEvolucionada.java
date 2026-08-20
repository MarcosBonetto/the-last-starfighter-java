package modelo;

public class DecoratorNaveEvolucionada extends DecoratorSpaceship {

    private long inicioBuff;
    private static final long DURACION_MS = 20000; // 20 segundos

    public DecoratorNaveEvolucionada(spaceship_1 naveDecorada) {
        super(naveDecorada);
        this.inicioBuff = System.currentTimeMillis();
    }

    // Renovar el tiempo si vuelve a evolucionar
    public void refrescar() {
        this.inicioBuff = System.currentTimeMillis();
    }

    private boolean buffActivo() {
        long ahora = System.currentTimeMillis();
        return (ahora - inicioBuff) <= DURACION_MS;
    }

    // Tiempo restante en ms
    public long getMsRestantes() {
        long ahora = System.currentTimeMillis();
        long restante = DURACION_MS - (ahora - inicioBuff);
        return Math.max(0, restante);
    }

    // Tiempo restante en segundos (redondeado para arriba)
    public int getSegundosRestantes() {
        return (int) Math.ceil(getMsRestantes() / 1000.0);
    }

    @Override
    public int getSpeed() {
        // Sólo aumenta la velocidad mientras el buff esté activo
        if (buffActivo()) {
            return super.getSpeed() + 7; // +7 de velocidad mientras dure la evo
        }
        return super.getSpeed();
    }

    @Override
    public misil Ataque(personaje_base jugador) {
        misil m = naveDecorada.Ataque(jugador);
        if (m != null && buffActivo()) {
            m.setDamage(m.getDamage() + 1);// +1 de daño mientras dure la evo
            m.setSpeed((int)(m.getSpeed() * 1.7)); // +70% de velocidad mientras dure la evo
        }
        return m;
    }
    
}
