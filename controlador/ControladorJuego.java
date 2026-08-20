package controlador;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;
import modelo.EnemigoFactory;
import modelo.Enemigo_Fantasma;
import modelo.misil;
import modelo.personaje_base;
import modelo.spaceship_1;

public class ControladorJuego {

    private spaceship_1 naveJugador;
    private boolean volandoIzquierda, volandoDerecha, volandoArriba, volandoAbajo, disparando;
    private final List<personaje_base> enemigos;
    private final List<misil> misilesJugador;
    private int puntaje;
    private long ultimaNave = 0;
    private long ultimoDisparo = 0;
    private final long tiempoInicio;
    private long spawnIntervalMs = 5000;  // intervalo base
    private long dificultadInicio = System.currentTimeMillis();

    
    //power up
    private java.util.Timer powerupTimer;
    private int powerupMinMs = 15000;
    private int powerupMaxMs = 35000;
    private volatile boolean vidaActive = false;
    private volatile boolean misilActive = false;
    private int vidaX = 0, vidaY = 0;
    private int misilX = 0, misilY = 0;
    private final int powerUpSize = 48;
    private int enemigosEliminados = 0;
    private int objetivoEvolucion = 15;


    public ControladorJuego(spaceship_1 nave, List<personaje_base> enemigos) {
        this.naveJugador = nave;
        this.enemigos = new CopyOnWriteArrayList<>(enemigos);
        this.misilesJugador = new ArrayList<>();
        this.puntaje = 0;
        this.tiempoInicio = System.currentTimeMillis();
    }

    // actualiza la logica del juego
    public void actualizarJuego(List<misil> misilesJugador, List<misil> misilesEnemigos, int panelWidth, int panelHeight) {
        
        int speed = naveJugador.getSpeed();
        if (volandoIzquierda) {
            naveJugador.setX( Math.max(0, naveJugador.getX() - speed) );
        }
        if (volandoDerecha) {
            naveJugador.setX( Math.min(panelWidth - naveJugador.getWidth(), naveJugador.getX() + speed) );
        }
        if (volandoArriba) {
            naveJugador.setY( Math.max(0, naveJugador.getY() - speed) );
        }
        if (volandoAbajo) {
            naveJugador.setY( Math.min(panelHeight - naveJugador.getHeight(), naveJugador.getY() + speed) );
        }
        if (disparando) {
            dispararJugador();
        }
        
        
        if (this.vidaActive) {
            Rectangle r = new Rectangle(this.vidaX, this.vidaY, this.powerUpSize, this.powerUpSize);
            if (r.intersects(naveJugador.getBounds())) {
                AudioManager.getInstancia().reproducirFX("sonidos/PowerUp.wav");
                 if (naveJugador instanceof modelo.DecoratorVidaExtra decoVida) {
                    decoVida.agregarVidaExtra();
        } else {
            naveJugador = new modelo.DecoratorVidaExtra(naveJugador);
        }

        this.vidaActive = false;
        scheduleNextPowerup(panelWidth, panelHeight);
    }
}

        if (this.misilActive) {
            java.awt.Rectangle r2 = new java.awt.Rectangle(this.misilX, this.misilY, this.powerUpSize, this.powerUpSize);
            if (r2.intersects(naveJugador.getBounds())) {
                AudioManager.getInstancia().reproducirFX("sonidos/PowerUp.wav");
                if (naveJugador instanceof modelo.DecoratormisilX3 decoMisil) {
                    decoMisil.refrescar();
        } else {
             naveJugador = new modelo.DecoratormisilX3(naveJugador);
        }

        this.misilActive = false;
        scheduleNextPowerup(panelWidth, panelHeight);
    }
}


        // Mover enemigos
        Random rnd = new Random();
        for (personaje_base enemigo : enemigos) {
            enemigo.movimiento();
            
    if (rnd.nextInt(100) < 2) {
        misil nuevoMisil = enemigo.Ataque(naveJugador);
        if (nuevoMisil != null) {
            misilesEnemigos.add(nuevoMisil);
        }
    }

    // Colisión enemigo - jugador
    if (colision(naveJugador, enemigo)) {
        // Fantasma invisible lo atraviesa
        if (enemigo instanceof Enemigo_Fantasma fantasma
                && fantasma.isInvisibleFantasma()) {
            continue;
        }
        naveJugador.recibirDanio(1);
        // si muere el jugador
        if (!naveJugador.estaVivo()) {
            AudioManager.getInstancia().reproducirFX("sonidos/MuerteNave.wav");
        }
    }
}



        // Mover misiles
        misilesJugador.forEach(m -> m.MovimientoMisil());
        misilesEnemigos.forEach(m -> m.MovimientoMisil());

        // Colisiones misiles del jugador con enemigos
        verificarImpactos(misilesJugador);

        // Colisiones misiles enemigos con el jugador
        verificarImpactosJugador(misilesEnemigos);
    }
        
       

    public void dispararJugador() {
    long now = System.currentTimeMillis();
    if (now - ultimoDisparo >= 300) {
        misilesJugador.add(naveJugador.Ataque(naveJugador));
        AudioManager.getInstancia().reproducirFX("sonidos/DisparoNave.wav");
        ultimoDisparo = now;
    }
    }

    public List<misil> getMisilesJugador() {
        return misilesJugador;
    }

    public List<personaje_base> getEnemigos() {
        return enemigos;
    }

    private void verificarImpactos(List<misil> misilesJugador) {
    Iterator<misil> itM = misilesJugador.iterator();
    while (itM.hasNext()) {
        misil m = itM.next();
        Iterator<personaje_base> itE = enemigos.iterator();
        while (itE.hasNext()) {
            personaje_base e = itE.next();

            if (colision(m, e)) {
                if (e instanceof Enemigo_Fantasma fantasma
                        && fantasma.isInvisibleFantasma()) {
                  
                    break;
                }
                e.recibirDanio(m.getDamage());
                itM.remove();

                if (!e.estaVivo()) {
                    puntaje += e.getPuntos();
                    enemigos.remove(e);
                    AudioManager.getInstancia().reproducirFX("sonidos/MuerteEnemigo.wav");

                    //CONTADOR EVO
                    enemigosEliminados++;
                    if (enemigosEliminados >= objetivoEvolucion) {
                        evolucionarNave();
                        enemigosEliminados = 0; // resetea para la próxima evo
                    }
                }
                break;
            }
        }
    }
}


    

    private void verificarImpactosJugador(List<misil> misilesEnemigos) {
        Iterator<misil> it = misilesEnemigos.iterator();
        while (it.hasNext()) {
            misil m = it.next();
            if (colision(m, naveJugador)) {
                naveJugador.recibirDanio(m.getDamage());
                it.remove();
                if (!naveJugador.estaVivo()) {
                AudioManager.getInstancia().reproducirFX("sonidos/MuerteNave.wav");
            }
            }
        }
    }

    public void agregaEnemigo(int panelWidth, int panelHeight) {
    long now = System.currentTimeMillis();

    if (panelHeight < 200) panelHeight = 600;

    long tiempoJugado = now - dificultadInicio;
    long reduccion = (tiempoJugado / 20000) * 200;
    long intervaloActual = Math.max(1500, spawnIntervalMs - reduccion);
    if (now - ultimaNave >= intervaloActual) {
        enemigos.add(EnemigoFactory.crearEnemigo(panelWidth, panelHeight, tiempoInicio, now));
        ultimaNave = now;
    }
}
    ////////////////////////////POWER UPS///////////////////////////////////////////
    /**
     * Configura el scheduler de power-ups: intervalo mínimo y máximo en segundos.
     * Llama a scheduleNextPowerup() para iniciar la primera aparición.
     */
    
    public void configurarPowerUps(int panelWidth, int panelHeight, int minSeconds, int maxSeconds) {
        if (minSeconds < 0) minSeconds = 0;
        if (maxSeconds < minSeconds) maxSeconds = minSeconds;
        this.powerupMinMs = minSeconds * 1000;
        this.powerupMaxMs = maxSeconds * 1000;
        if (this.powerupTimer != null) {
            this.powerupTimer.cancel();
        }
        this.powerupTimer = new Timer(true);
        scheduleNextPowerup(panelWidth, panelHeight);
    }

    public void detenerPowerUps() {
        if (this.powerupTimer != null) {
            this.powerupTimer.cancel();
            this.powerupTimer = null;
        }
        vidaActive = false;
        misilActive = false;
    }

    public void scheduleNextPowerup(int width, int height) {
        if (this.powerupTimer == null) return;
        final Random rnd = new Random();
        final int delay = this.powerupMinMs + rnd.nextInt(Math.max(1, this.powerupMaxMs - this.powerupMinMs + 1));
        this.powerupTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // si ya hay uno activo, no hacemos nada
                if (vidaActive || misilActive) return;
                if (width <= 0 || height <= 0) {
                    // panel no inicializado aún, reprogramar
                    scheduleNextPowerup(width, height);
                    return;
                }
                final int px = 50 + rnd.nextInt(Math.max(1, width - 100));
                final int py = 50 + rnd.nextInt(Math.max(1, height - 100));
                final boolean spawnVida = rnd.nextBoolean();
                // actualizar estado en EDT
                SwingUtilities.invokeLater(() -> {
                    if (spawnVida) {
                        vidaX = px; vidaY = py; vidaActive = true;
                    } else {
                        misilX = px; misilY = py; misilActive = true;
                    }
                });
            }
        }, delay);
    }

    public spaceship_1 getNaveJugador(){
        return this.naveJugador;
    }
    public void setVolandoIzquierda(boolean s) { 
        this.volandoIzquierda = s; 
    }

    public void setVolandoDerecha(boolean s) { 
        this.volandoDerecha = s; 
    }

    public void setVolandoArriba(boolean s) { 
        this.volandoArriba = s; 
    }
    
    public void setVolandoAbajo(boolean s) { 
        this.volandoAbajo = s; 
    }

    public void setDisparando(boolean s) { 
        this.disparando = s; 
    }

    private boolean colision(personaje_base a, personaje_base b) {
        return a.getBounds().intersects(b.getBounds());
    }

    private boolean colision(misil m, personaje_base p) {
        return m.getBounds().intersects(p.getBounds());
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean isVidaActive() { 
        return vidaActive; 
    }

    public int getVidaX() { 
        return vidaX; 
    }

    public int getVidaY() { 
        return vidaY; 
    }

    public int getPowerUpSize() { 
        return powerUpSize; 
    }

    public void desactivarVida() { 
        vidaActive = false; 
    }

    public boolean isMisilActive() {
        return misilActive;
    }

    public void desactivarMisil() {
        this.misilActive = false;
    }


    public int getMisilX() {
        return misilX;
    }

    public int getMisilY() {
        return misilY;
    }
    
    public boolean isBuffMisilX3Activo() {
        if (naveJugador instanceof modelo.DecoratormisilX3 deco) {
            return deco.getMsRestantes() > 0;
        }
        return false;
    }

    
    public int getSegundosRestantesMisilX3() {
        if (naveJugador instanceof modelo.DecoratormisilX3 deco) {
            return deco.getSegundosRestantes();
        }
        return 0;
    }

    // Para que el panel pueda mostrar el contador
    public int getEnemigosEliminados() {
        return enemigosEliminados;
    }

    public int getObjetivoEvolucion() {
        return objetivoEvolucion;
    }
    
    public void setObjetivoEvolucion(int objetivoEvolucion) {
        this.objetivoEvolucion = objetivoEvolucion;
    }
    
    private void evolucionarNave() {
    modelo.DecoratorNaveEvolucionada evo = buscarEvo();
    if (evo != null) {
        // Si ya hay una EVO en la cadena, solo renovamos el tiempo
        evo.refrescar();
    } else {
        // No hay EVO, envolvemos la nave actual
        naveJugador = new modelo.DecoratorNaveEvolucionada(naveJugador);
    }
    //audio evo
    AudioManager.getInstancia().reproducirEvo("sonidos/SoundtrackEVO.wav");
    AudioManager.getInstancia().setVolumeEvo(-20.0f);
    // Después de 20 segundos, vuelvo a la música normal
    new java.util.Timer().schedule(
        new java.util.TimerTask() {
            @Override
            public void run() {
                AudioManager.getInstancia().resumeMusica();
            }
        },
        20000//20 segundos
    );
}


public boolean isNaveEvoActiva() {
    modelo.DecoratorNaveEvolucionada evo = buscarEvo();
    return evo != null && evo.getMsRestantes() > 0;
}

public int getSegundosRestantesNaveEvo() {
    modelo.DecoratorNaveEvolucionada evo = buscarEvo();
    return (evo != null) ? evo.getSegundosRestantes() : 0;
}


// Busca si en la cadena de decorators hay un DecoratorNaveEvolucionada
private modelo.DecoratorNaveEvolucionada buscarEvo() {
    spaceship_1 actual = naveJugador;

    while (actual instanceof modelo.DecoratorSpaceship dec) {
        if (actual instanceof modelo.DecoratorNaveEvolucionada evo) {
            return evo;
        }
        actual = dec.getNaveDecorada();  // avanzar un nivel hacia adentro
    }
    return null;
}

}
