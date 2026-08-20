package vista;


import javax.imageio.ImageIO;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FondoAnimado {
    private BufferedImage bgBack, bgStars, bgNear;
    private int offBack = 0, offStars = 0, offNear = 0;
    private final int spBack = -1, spStars = -3, spNear = -6;
    

    public FondoAnimado() {
        cargarFondos();
        //Timer fondoLoop = new Timer(16, e -> {
        //    actualizarOffsets();
        //});
        //fondoLoop.start();
    }

    private void cargarFondos() {
        try {
            bgBack  = ImageIO.read(new File("fondo/Old Version/layers/fondo.png"));
            bgStars = ImageIO.read(new File("fondo/Old Version/layers/planetasfondo.png"));
            bgNear  = ImageIO.read(new File("fondo/Old Version/layers/estrellas.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarOffsets() {
        offBack  += spBack;
        offStars += spStars;
        offNear  += spNear;
    }

    private void dibujarFondo(Graphics2D g, int W, int H) {
        if (W <= 0 || H <= 0 || bgBack == null) return;

        dibujarCapa(g, bgBack,  offBack,  W, H);
        dibujarCapa(g, bgStars, offStars, W, H);
        dibujarCapa(g, bgNear,  offNear,  W, H);
    }

    private void dibujarCapa(Graphics2D g, BufferedImage img, int offset, int W, int H) {
        int iw = img.getWidth(), ih = img.getHeight();
        int drawH = H;
        int drawW = (int) Math.ceil(iw * (drawH / (double) ih));
        int start = ((offset % drawW) + drawW) % drawW;

        for (int x = -drawW + start; x < W; x += drawW) {
            g.drawImage(img, x, 0, drawW, drawH, null);
        }
    }
    public void render (Graphics g, int W, int H){
        dibujarFondo((Graphics2D)g, W, H);
    }


}

    


