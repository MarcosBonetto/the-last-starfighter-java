package modelo;

import java.util.Random;

public class EnemigoFactory {

    private static final int PROBABILIDAD_VIOLETA_PCT = 30;
    private static final int PROBABILIDAD_FANTASMA_PCT = 70;

    public static personaje_base crearEnemigo(int panelWidth, int panelHeight, long tiempoInicio, long tiempoActual) {
        Random r = new Random();

        int margenSuperior = 30;
        int margenInferior = 30;
        int alturaEnemigo = 100;
        int rangoY = panelHeight - alturaEnemigo - margenInferior - margenSuperior;
        if (rangoY < 10) rangoY = 10;
        int y = r.nextInt(rangoY) + margenSuperior;

        int speedY = r.nextInt(5) + 1;
        if (r.nextBoolean()) speedY = -speedY;
        if (speedY == 0) speedY = 2;

        long segundosTranscurridos = (tiempoActual - tiempoInicio) / 1000;

        int xSpawn = panelWidth + 50;

        if (segundosTranscurridos > 30) {
            int chanceFantasma = r.nextInt(100);
            if (chanceFantasma < PROBABILIDAD_FANTASMA_PCT) {
                // x, y, width, height, speed
                return new Enemigo_Fantasma(xSpawn, y, 120, 120, 4);
            }
        }

        if (segundosTranscurridos > 15) {
            int chance = r.nextInt(100);
            //30% de probabilidad de que aparezcan los enemigos violetas
            if (chance < PROBABILIDAD_VIOLETA_PCT){
                return new Enemigo_Violeta(xSpawn, y, 100, 100, 3, 6, 0, false);
            }
        }
        
        return new Enemigo_Rojo(xSpawn, y, 100, 100, 4, 5, speedY, false);

    }
}