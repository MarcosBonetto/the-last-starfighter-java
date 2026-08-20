package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.AudioManager;

public class PantallaInstrucciones extends JPanel {

    private final FondoAnimado fondo = new FondoAnimado();

    // Define las fuentes y colores principales
    private final Font fontTitulo = new Font("Impact", Font.BOLD, 48);
    private final Font fontSubtitulo = new Font("Impact", Font.PLAIN, 32);
    private final Font fontTexto = new Font("Arial", Font.PLAIN, 18);
    private final Font fontTextoEnemigo = new Font("Arial", Font.BOLD, 16);
    private final Color colorTexto = new Color(230, 230, 230);

    // Constructor principal de la pantalla
    public PantallaInstrucciones() {
        // El panel principal usa BorderLayout para superponer el scroll
        setLayout(new BorderLayout()); 
        
        // Creamos un panel de contenido que sera transparente
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(40, 80, 40, 80));
        contentPanel.setOpaque(false);

        // Añadimos las secciones al panel de contenido
        JLabel lblTitulo = crearLabel("COMO SE JUEGA?", fontTitulo);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTitulo);

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionObjetivo());
        contentPanel.add(Box.createVerticalStrut(30)); 
        contentPanel.add(crearSeccionPlayer());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionEnemigos());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionRanking());
        contentPanel.add(Box.createVerticalGlue()); 
        contentPanel.add(Box.createVerticalStrut(50));

        // Boton para regresar al menu
        JButton volver = new JButton("VOLVER");
        volver.setFont(fontSubtitulo);
        volver.setAlignmentX(Component.CENTER_ALIGNMENT);
        volver.addActionListener(e -> {
            AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (top instanceof Ventanas ventana) {
                ventana.mostrarPantalla("INICIO");
            }
        });
        contentPanel.add(volver);

        // Creamos el JScrollPane para el contenido
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Ajusta la velocidad del scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Añadimos el scroll al panel principal
        add(scrollPane, BorderLayout.CENTER);

        startFondoLoop();
    }

    // Metodo para crear la seccion de objetivo
    private JPanel crearSeccionObjetivo() {
        JPanel panel = crearPanelSeccion();
        
        JLabel subtitulo = crearLabel("OBJETIVO", fontSubtitulo);
        JLabel texto = crearLabel("<html>El objetivo del juego es alcanzar el mayor puntaje posible sin perder tus 5 vidas y quedar registrado en la tabla de los mejores puntajes.</html>", fontTexto);
        
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(texto);
        
        return panel;
    }

    // Metodo para crear la seccion del jugador
    private JPanel crearSeccionPlayer() {
        JPanel panel = crearPanelSeccion();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel panelTexto = crearPanelSeccion();
        JLabel subtitulo = crearLabel("PERSONAJE PRINCIPAL", fontSubtitulo);
        JLabel texto = crearLabel("<html>Nuestro personaje jugable principal tendrá la posibilidad de moverse por toda la pantalla y disparar proyectiles a los enemigos que se presenten en el mapa.</html>", fontTexto);
        
        panelTexto.add(subtitulo);
        panelTexto.add(Box.createVerticalStrut(10));
        panelTexto.add(texto);
        panelTexto.add(Box.createVerticalStrut(10));
        
        panelTexto.add(crearPanelStats("Salud:", "img_varias/vidas.png", 5));
        
        JPanel panelImagen = crearPanelSeccion();
        
        ImageIcon imgPlayer = new ImageIcon("nave/nave1.png"); 
        Image img = imgPlayer.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        panelImagen.add(new JLabel(scaledIcon));

        panel.add(panelTexto);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(panelImagen);
        return panel;
    }

    // Metodo para crear la seccion de enemigos
    private JPanel crearSeccionEnemigos() {
        JPanel panel = crearPanelSeccion();
        
        JLabel subtitulo = crearLabel("ENEMIGOS", fontSubtitulo);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitulo);
        add(Box.createVerticalStrut(10));

        JPanel panelTipos = crearPanelSeccion();
        panelTipos.setLayout(new BoxLayout(panelTipos, BoxLayout.X_AXIS));
        
        panelTipos.add(crearSubSeccionEnemigoRojo());
        panelTipos.add(Box.createHorizontalStrut(50));
        panelTipos.add(crearSubSeccionEnemigoVioleta());
        panelTipos.add(Box.createHorizontalStrut(50));
        panelTipos.add(crearSubSeccionEnemigoFantasma());
        
        panel.add(panelTipos);
        return panel;
    }

    // Metodo para crear el sub panel del enemigo rojo
    private JPanel crearSubSeccionEnemigoRojo() {
        JPanel panel = crearPanelSeccion();
        panel.add(crearLabel("ENEMIGO ROJO", fontSubtitulo));
        
        ImageIcon imgRojo = new ImageIcon("Enemigo_rojo/Enemigo_rojo_1.png");
        Image img = imgRojo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel lblImgRojo = new JLabel(scaledIcon);
        
        lblImgRojo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblImgRojo);
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearLabel("Movimiento: ZIG ZAG", fontTextoEnemigo));
        panel.add(crearLabel("Ataque: Disparo lineal", fontTextoEnemigo));
        panel.add(crearPanelStats("Salud:", "img_varias/vidas.png", 4));
        panel.add(crearPanelStats("Daño:", "img_varias/misilEnemigoRojo.png", 1));

        return panel;
    }

    // Metodo para crear el sub panel del enemigo violeta
    private JPanel crearSubSeccionEnemigoVioleta() {
        JPanel panel = crearPanelSeccion();
        panel.add(crearLabel("ENEMIGO VIOLETA", fontSubtitulo));

        ImageIcon imgVioleta = new ImageIcon("Enemigo_violeta/Enemigo_violeta_1.png");
        Image img = imgVioleta.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel lblImgVioleta = new JLabel(scaledIcon);
        
        lblImgVioleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblImgVioleta);
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearLabel("Ataque: Dispara en dirección al jugador", fontTextoEnemigo));
        panel.add(crearPanelStats("Salud:", "img_varias/vidas.png", 3));
        panel.add(crearPanelStats("Daño:", "img_varias/misilEnemigoRojo.png", 2));

        return panel;
    }

    // Metodo para crear el sub panel del enemigo fantasma
    private JPanel crearSubSeccionEnemigoFantasma() {
    JPanel panel = crearPanelSeccion();
    panel.add(crearLabel("ENEMIGO FANTASMA", fontSubtitulo));

    // ⚠ Cambiá la ruta al PNG del fantasma según tu carpeta
    ImageIcon imgFantasma = new ImageIcon("Enemigo_fantasma/SpriteFantasmaV2.png");
    Image img = imgFantasma.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(img);
    JLabel lblImgFantasma = new JLabel(scaledIcon);

    lblImgFantasma.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(lblImgFantasma);
    panel.add(Box.createVerticalStrut(10));

    // Texto descriptivo
    panel.add(crearLabel("Movimiento: Horizontal", fontTextoEnemigo));
    panel.add(crearLabel("Habilidad: Se vuelve fantasma e invulnerable unos segundos y vuelve al lugar de spawn", fontTextoEnemigo));
    panel.add(crearLabel("Ataque: Disparo lineal", fontTextoEnemigo));

    // Stats (ajustá números si en el código son otros)
    panel.add(crearPanelStats("Salud:", "img_varias/vidas.png", 3));
    panel.add(crearPanelStats("Daño:", "img_varias/misilEnemigoRojo.png", 1));

    return panel;
}


    // Metodo para crear la seccion de ranking
    private JPanel crearSeccionRanking() {
        JPanel panel = crearPanelSeccion();
        
        JLabel subtitulo = crearLabel("RANKING DE JUGADORES", fontSubtitulo);
        JLabel texto = crearLabel("<html>El juego contará con una tabla que almacenará los datos del top 5 de los mejores jugadores junto con su mayor puntaje registrado, la misma será implementada con SQLite.</html>", fontTexto);
        
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(texto);
        
        return panel;
    }

    // Metodo de ayuda para crear labels con estilo
    private JLabel crearLabel(String texto, Font font) {
        JLabel label = new JLabel(texto);
        label.setFont(font);
        label.setForeground(colorTexto);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Metodo de ayuda para crear un panel transparente
    private JPanel crearPanelSeccion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
    
    // Metodo de ayuda para mostrar stats con iconos
    private JPanel crearPanelStats(String label, String imgPath, int count) {
        JPanel panel = crearPanelSeccion();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        panel.add(crearLabel(label, fontTextoEnemigo));
        panel.add(Box.createHorizontalStrut(10));
        
        for (int i = 0; i < count; i++) {
            panel.add(new JLabel(scaledIcon));
        }
        
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    // Dibuja el fondo animado
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        fondo.render(g, getWidth(), getHeight());
    }

    // Inicia el timer para el fondo
    private void startFondoLoop() {
        javax.swing.Timer fondoLoop = new javax.swing.Timer(16, e -> {
            fondo.actualizarOffsets();
            repaint();
        });
        fondoLoop.start();
    }
}