package controlador;

import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {

    private static AudioManager instancia;

    // guardamos la música de fondo actual
    private Clip musicaActual;
    private Clip musicaEvo;


    private AudioManager() {}

    public static AudioManager getInstancia() {
        if (instancia == null) {
            instancia = new AudioManager();
        }
        return instancia;
    }

    // ---------- FX (sonidos cortos) ----------
    public void reproducirFX(String ruta) {
        try {
            File archivo = new File(ruta);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();         // suena una vez

        } catch (Exception e) {
            System.out.println("Error FX: " + ruta);
            e.printStackTrace();
        }
    }

    // ---------- MÚSICA EN LOOP ----------
    public void reproducirMusicaLoop(String ruta) {
        try {
            stopMusica();
            stopEvo();
            File archivo = new File(ruta);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            musicaActual = AudioSystem.getClip();
            musicaActual.open(audioStream);
            musicaActual.loop(Clip.LOOP_CONTINUOUSLY);
            musicaActual.start();

        } catch (Exception e) {
            System.out.println("Error música: " + ruta);
            e.printStackTrace();
        }
    }

    public void reproducirEvo(String ruta) {
    try {
        stopEvo(); // por si quedaba sonando

        File archivo = new File(ruta);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);

        musicaEvo = AudioSystem.getClip();
        musicaEvo.open(audioStream);
        musicaEvo.start(); // suena una vez

    } catch (Exception e) {
        System.out.println("Error EVO: " + ruta);
        e.printStackTrace();
    }
}


    public void stopMusica() {
        if (musicaActual != null) {
            musicaActual.stop();
            musicaActual.close();
            musicaActual = null;
        }
    }

    public void pauseMusica() {
    // Pausa música normal
    if (musicaActual != null && musicaActual.isRunning()) {
        musicaActual.stop();
    }

    // Pausa EVO
    if (musicaEvo != null && musicaEvo.isRunning()) {
        musicaEvo.stop();
    }
}

public void resumeMusica() {
    // Reanudar música normal
    if (musicaActual != null && !musicaActual.isRunning()) {
        musicaActual.start();
    }

    // Reanudar EVO si seguía dentro de los 20s
    if (musicaEvo != null && !musicaEvo.isRunning()) {
        musicaEvo.start();
    }
}

public void stopEvo() {
    if (musicaEvo != null) {
        musicaEvo.stop();
        musicaEvo.close();
        musicaEvo = null;
    }
}

private void setVolume(Clip clip, float volumen) {
    if (clip == null) return;
    if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(volumen); // volumen en decibelios
    }
}

public void setVolumeMusica(float volumenDb) {
    setVolume(musicaActual, volumenDb);
}

public void setVolumeEvo(float volumenDb) {
    setVolume(musicaEvo, volumenDb);
}


}

