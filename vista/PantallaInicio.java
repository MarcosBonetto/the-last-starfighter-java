package vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controlador.AudioManager;

 public class PantallaInicio extends JPanel {

    
    private final FondoAnimado fondo = new FondoAnimado();

    public PantallaInicio(Ventanas ventana){
        setLayout(null);//Posicionamiento Manual

        int anchoVentanaAprox = 1280;

        // Titulo Principal
        ImageIcon imagentitulo = new ImageIcon("Img_varias/titulo.png");
        Image titulo = imagentitulo.getImage().getScaledInstance(900, 350, Image.SCALE_SMOOTH);
        ImageIcon imgTitulo = new ImageIcon(titulo);
        JLabel Labeltitulo = new JLabel(imgTitulo);

        int xTitulo = (anchoVentanaAprox - 900) / 2; // centrado aprox
        Labeltitulo.setBounds(xTitulo, 40, 900, 350);
        add(Labeltitulo);

        // Boton Jugar (centrado debajo del título)
        ImageIcon imagenPlay = new ImageIcon("Img_varias/btnJugar.png");
        Image jugar = imagenPlay.getImage().getScaledInstance(200,80 , Image.SCALE_SMOOTH);
        ImageIcon imgJugar2 = new ImageIcon(jugar);
        JButton btnJugar = new JButton(imgJugar2);
        int xJugar = (anchoVentanaAprox - 200) / 2;
        btnJugar.setBounds(xJugar, 420, 200, 80);
        btnJugar.setBorderPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setFocusPainted(false);
        btnJugar.addActionListener(e -> {
        AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
        ventana.empezarjuego();
        });

        add(btnJugar);

        // Boton Como jugar (centrado más abajo)
        ImageIcon ImagenComo = new ImageIcon("Img_varias/btncomojugar.png");
        Image Howto = ImagenComo.getImage().getScaledInstance(300,80 , Image.SCALE_SMOOTH);
        ImageIcon imgHowto = new ImageIcon(Howto);
        JButton btnComoJugar = new JButton(imgHowto);
        int xComo = (anchoVentanaAprox - 300) / 2;
        btnComoJugar.setBounds(xComo, 520, 300, 80);        // antes 650 -> se iba afuera
        btnComoJugar.addActionListener(e -> {
        AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
        ventana.mostrarPantalla("COMO_JUGAR");
        });
        btnComoJugar.setBorderPainted(false);
        btnComoJugar.setContentAreaFilled(false);
        btnComoJugar.setFocusPainted(false);
        add(btnComoJugar);

        // Boton de Ranking (abajo a la derecha pero dentro de la ventana)
        ImageIcon ImagenRank = new ImageIcon("Img_varias/rank.png");
        Image rank = ImagenRank.getImage().getScaledInstance(150,150 , Image.SCALE_SMOOTH);
        ImageIcon imgrank = new ImageIcon(rank);
        JButton btnRank = new JButton(imgrank);
        int xRank = anchoVentanaAprox - 180; // un poco separado del borde derecho
        btnRank.setBounds(xRank, 500, 150, 150);           // antes 1300,630 -> se iba afuera
        btnRank.addActionListener(e -> {
        AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
        ventana.mostrarRanking();
        });
        btnRank.setBorderPainted(false);
        btnRank.setContentAreaFilled(false);
        btnRank.setFocusPainted(false);
        add(btnRank);

        javax.swing.Timer fondoLoop = new javax.swing.Timer(16, e -> {
            fondo.actualizarOffsets();
            repaint();
        });
        fondoLoop.start();
    }
     @Override
     public void paintComponent(Graphics g){
        super.paintComponent(g);
        fondo.render(g, getWidth(), getHeight());
    }
}
