package vista;

import controlador.ControladorRanking;
import controlador.Gameloop;
import controlador.controlador;
import java.awt.CardLayout;
import javax.swing.*;
import controlador.AudioManager;

public class Ventanas extends JFrame {
    private final CardLayout layout;
    private final JPanel contenedor;
    private final panel panelJuego;
    private final PantallaInstrucciones PantallaInstrucciones;
    private final PantallaRanking pantallaRanking;
    private final ControladorRanking ctrlRanking = new ControladorRanking();
    private final Gameloop gameloop;

    // Estado de pantalla completa
    private boolean fullscreen = false;

    public Ventanas(){

        layout = new CardLayout();
        contenedor = new JPanel(layout);

        // Pantalla de inicio
        PantallaInicio inicio = new PantallaInicio(this);
        contenedor.add(inicio, "INICIO");

        // Pantalla de ranking
        pantallaRanking = new PantallaRanking(this);
        contenedor.add(pantallaRanking, "RANKING");

        // Pantalla de juego y pausa
        panelJuego = new panel();
        gameloop = new Gameloop(panelJuego);

        panelJuego.setOnGameOver(puntajeFinal -> {
            gameloop.stop();
            AudioManager audio = AudioManager.getInstancia();
            audio.stopMusica(); 
            audio.stopEvo();
            String nombre = JOptionPane.showInputDialog(this, "INGRESA TU NOMBRE:");

            if (nombre != null && !nombre.trim().isEmpty()) {
                ctrlRanking.guardarPuntaje(nombre.trim(), puntajeFinal);
                pantallaRanking.actualizarRanking(ctrlRanking.obtenerTop5());
                mostrarPantalla("RANKING");
            } else {
                mostrarPantalla("INICIO");
            }
        });

        panelJuego.setOnExitToMenu(() -> {
            gameloop.stop();
            AudioManager audio = AudioManager.getInstancia();
            audio.stopMusica();
            audio.stopEvo();
            mostrarPantalla("INICIO");
        });
        contenedor.add(panelJuego, "JUEGO");

        // Pantalla de instrucciones
        PantallaInstrucciones = new PantallaInstrucciones();
        contenedor.add(PantallaInstrucciones,"COMO_JUGAR");

        // Agrego el contenedor principal
        add(contenedor);

        setUndecorated(false);
        setResizable(false);

        // Tamaño base razonable
        setSize(1280, 720);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Control de teclas del juego 
        controlador control = new controlador(panelJuego);
        panelJuego.addKeyListener(control);

        
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("F11"), "toggleFS");
        am.put("toggleFS", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                toggleFullscreen();
            }
        });
    }

    // Cambia entre ventana 1280x720 y pantalla completa
    private void toggleFullscreen() {
        fullscreen = !fullscreen;

        // guardo el tamaño actual por si en algún momento querés usarlo
        // Dimension current = getSize();

        dispose(); // necesario para cambiar undecorated

        if (fullscreen) {
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
            setSize(1280, 720);
            setLocationRelativeTo(null);
        }

        setVisible(true);
    }

    public void mostrarPantalla(String nombre) {
        layout.show(contenedor, nombre);
        if ("JUEGO".equals(nombre)) {
            panelJuego.setFocusable(true);
            panelJuego.requestFocusInWindow();
        }
    }

    public void empezarjuego() {
        AudioManager.getInstancia().reproducirMusicaLoop("sonidos/Soundtrack.wav");
        AudioManager.getInstancia().setVolumeMusica(-10.0f);
        panelJuego.reiniciarJuego();
        panelJuego.setPaused(false);
        gameloop.start();
        mostrarPantalla("JUEGO");
    }

    public void pausajuego() {
        panelJuego.setPaused(true);
        gameloop.stop();
        AudioManager.getInstancia().pauseMusica();
    }

    public void reanudarJuego() {
    panelJuego.setPaused(false);
    gameloop.start();
    AudioManager.getInstancia().resumeMusica();
}


    public panel getPanelJuego(){
        return panelJuego;
    }

    public PantallaRanking getPantallaRanking() {
        return pantallaRanking;
    }
    
    public void mostrarRanking() {
        pantallaRanking.actualizarRanking(ctrlRanking.obtenerTop5());
        mostrarPantalla("RANKING");
    }
}
