package controlador; 

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import vista.panel; 

public class controlador implements KeyListener {
    
    private final panel panelJuego; 

    public controlador(panel panelJuego) { 
        this.panelJuego = panelJuego; 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControladorJuego controladorActual = panelJuego.getControladorJuego();
        if (controladorActual == null) return; 

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            controladorActual.setVolandoIzquierda(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            controladorActual.setVolandoDerecha(true); 
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            controladorActual.setVolandoArriba(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            controladorActual.setVolandoAbajo(true); 
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controladorActual.setDisparando(true); 
        }
   }

    @Override
    public void keyReleased(KeyEvent e) {
        ControladorJuego controladorActual = panelJuego.getControladorJuego();
        if (controladorActual == null) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            controladorActual.setVolandoIzquierda(false); 
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            controladorActual.setVolandoDerecha(false); 
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            controladorActual.setVolandoArriba(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            controladorActual.setVolandoAbajo(false); 
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controladorActual.setDisparando(false); 
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}