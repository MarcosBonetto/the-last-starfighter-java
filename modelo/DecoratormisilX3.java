package modelo;

public class DecoratormisilX3 extends DecoratorSpaceship {

    private long inicioBuff;
    private static final long DURACION_MS = 7000; // 7 segundos

    public DecoratormisilX3(spaceship_1 naveDecorada) {
        super(naveDecorada);
        this.inicioBuff = System.currentTimeMillis();
    }

    public void refrescar() {
        this.inicioBuff = System.currentTimeMillis();
    }

    private boolean buffActivo() {
        long ahora = System.currentTimeMillis();
        return (ahora - inicioBuff) <= DURACION_MS;
    }

    public long getMsRestantes() {
        long ahora = System.currentTimeMillis();
        long restante = DURACION_MS - (ahora - inicioBuff);
        return Math.max(0, restante);
    }

    //segundos redondeados
    public int getSegundosRestantes() {
        return (int) Math.ceil(getMsRestantes() / 1000.0);
    }

    @Override
    public misil Ataque(personaje_base jugador) {
        if (!buffActivo()) {
            return naveDecorada.Ataque(jugador);
        }
        misil m = naveDecorada.Ataque(jugador);
        m.setDamage(m.getDamage() * 3);
        return m;
    }
}

