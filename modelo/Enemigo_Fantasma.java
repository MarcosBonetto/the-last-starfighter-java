package modelo;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Enemigo_Fantasma extends personaje_base {

    private final Random random = new Random();
    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private volatile boolean visible = true;
    private final MovimientoEnemigo estrategiaMovimiento;

    public Enemigo_Fantasma(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, 3, speed, 0, false);
        this.estrategiaMovimiento = new MovimientoFantasmaVisible();
        iniciarCicloFantasma();
    }

    private void iniciarCicloFantasma() {
        scheduler.scheduleAtFixedRate(() -> {
            if (visible) {
                // se vuelve invisible, se queda donde está
                visible = false;
            } else {
                // vuelve visible en otra posición
                visible = true;
                int nuevaX = 1400 + random.nextInt(300);
                int nuevaY = 50 + random.nextInt(500);
                setX(nuevaX);
                setY(nuevaY);
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    @Override
    public void movimiento() {
        if (estrategiaMovimiento != null) {
            estrategiaMovimiento.mover(this);
        }
    }

    @Override
    public misil Ataque(personaje_base jugador) {
        misil m = new misil(getX(), getY() + getHeight() / 2 - 5, 10, 10, -15);
        m.setDamage(1);
        return m;
    }

    @Override
    public int getPuntos() {
        return 700;
    }

    @Override
    public String getTipoEnemigo() {
        return "fantasma";
    }

    public boolean isVisibleFantasma() {
        return visible;
    }

    public boolean isInvisibleFantasma() {
        return !visible;
    }

    public void detener() {
        scheduler.shutdownNow();
    }
}


