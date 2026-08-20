package controlador;

import vista.panel;

public class Gameloop {
    private final panel vista;
    private Thread loopThread;
    private volatile boolean running = false;

    public Gameloop(panel vista) {
        this.vista = vista;
    }

    public void start() {
        if (running) return;
        running = true;
        loopThread = new Thread(() -> {
            final int targetMs = 16; // ~60 FPS
            while (running) {
                long start = System.nanoTime();

                try {
                    vista.update();   // actualiza lógica
                    vista.repaint();  // dibuja la escena
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                long took = (System.nanoTime() - start) / 1_000_000;
                long sleep = targetMs - took;
                if (sleep > 0) {
                    try { Thread.sleep(sleep); } catch (InterruptedException e) { break; }
                } else {
                    Thread.yield();
                }
            }
        }, "GameLoop");
        loopThread.setDaemon(true);
        loopThread.start();
    }

    public void stop() {
        running = false;
        if (loopThread != null) {
            loopThread.interrupt();
            try { loopThread.join(200); } catch (InterruptedException ignored) {}
            loopThread = null;
        }
    }
}