package vista;

import controlador.AudioManager;
import controlador.ControladorJuego;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import modelo.*;
import controlador.AudioManager;

public class panel extends JPanel {

    private spaceship_1 player;
    private final List<misil> misilesEnemigos = new ArrayList<>();
    private final java.util.List<personaje_base> enemies = new CopyOnWriteArrayList<>();
    private BufferedImage spritePlayer1;
    private BufferedImage spritePlayer2;
    private BufferedImage spritePlayerHerido1;
    private BufferedImage SpritePlayerEvo1;
    private BufferedImage SpritePlayerEvo2;
    private BufferedImage SpritePlayerHeridoEvo1;
    private BufferedImage enemySprite;
    private BufferedImage enemySprite2;
    private BufferedImage enemySpriteHerido_Ene_rojo;
    private BufferedImage enemySpriteHerido_Ene_violeta;
    private BufferedImage misilSprite;
    private BufferedImage mslSprite_Enemigorojo;
    private BufferedImage enemySpriteVioleta;
    private BufferedImage vidas_img;
    private final BufferedImage[] fantasmaVisibleFrames   = new BufferedImage[3];
    private final BufferedImage[] fantasmaInvisibleFrames = new BufferedImage[3];

    private BufferedImage fantasmaHeridoVisible;
    private BufferedImage fantasmaHeridoInvisible;

    private int frameFantasma = 0;
    private int contadorFramesFantasma = 0;


    private boolean usarSpriteplayer = true;
    private boolean usarSpriteenemie = true;
    private int contadorFramesEnemigos = 0;
    private BufferedImage vidaExtra;
    private BufferedImage misilx3;
    private java.util.function.IntConsumer onGameOver;
    private boolean gameOverNotificado = false;
    private int contadorFramesPlayer = 0;



    private final FondoAnimado fondo = new FondoAnimado();

    private ControladorJuego controladorJuego;

    private boolean pausa = true;
    private javax.swing.JPanel pausaOverlay;
    private Runnable onExitToMenu;
    public void setOnExitToMenu(Runnable r) { this.onExitToMenu = r; }

    long ultimaNave = 0;

    public panel() {
        this.player = new spaceship_1(80, 270, 150, 150, 5, 12, 12, false);
        this.setDoubleBuffered(true);
        loadSprites();

        // Inicializa el controlador
        this.controladorJuego = new ControladorJuego(player, enemies);

        // Construye la UI de Pausa
        setLayout(new java.awt.BorderLayout());
        buildPausaUI();
        bindTeclaPausa();
    }

    public spaceship_1 getPlayer() {
        return player;
    }

    public ControladorJuego getControladorJuego() {
        return this.controladorJuego;
    }

    public synchronized List<misil> getMisilesEnemigosCopia() {
        return new ArrayList<>(misilesEnemigos);
    }

    public void update() {
        if (pausa) return;

        // ANIMACIÓN NAVE
        contadorFramesPlayer++;
        if (contadorFramesPlayer >= 12) {     // cada 12 updates
            usarSpriteplayer = !usarSpriteplayer;
            contadorFramesPlayer = 0;
        }

        // ANIMACIÓN ENEMIGOS ROJO / VIOLETA
        contadorFramesEnemigos++;
        if (contadorFramesEnemigos >= 12) { // cada 12 updates
            usarSpriteenemie = !usarSpriteenemie;
            contadorFramesEnemigos = 0;
        }

        // ANIMACIÓN FANTASMA
        contadorFramesFantasma++;
        if (contadorFramesFantasma >= 10) { // cada 10 updates 
            frameFantasma = (frameFantasma + 1) % 3; // 3 frames: 0,1,2
            contadorFramesFantasma = 0;
        }



        // actualiza la lógica del juego
        controladorJuego.actualizarJuego(controladorJuego.getMisilesJugador(),
        misilesEnemigos,
        getWidth(),
        getHeight());

        // Sincronizamos player del panel con navejugador del controlador
        this.player = controladorJuego.getNaveJugador();

        // actualiza el fondo
        fondo.actualizarOffsets();

        // agrega enemigos
        int w = getWidth()  > 0 ? getWidth()  : 1280;
        int h = getHeight() > 0 ? getHeight() : 720;
        controladorJuego.agregaEnemigo(w, h);


        if (!player.estaVivo() && !gameOverNotificado) {
            gameOverNotificado = true;
            if (controladorJuego != null) controladorJuego.detenerPowerUps();
            if (onGameOver != null) {
                javax.swing.SwingUtilities.invokeLater(() ->
            onGameOver.accept(controladorJuego.getPuntaje())
        );
    }
}

    }

    public void configurarPowerUps(int minSeconds, int maxSeconds) {
        controladorJuego.configurarPowerUps(getWidth(), getHeight(), minSeconds, maxSeconds);
    }

    // ====== DIBUJO ======
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            fondo.render(g, getWidth(), getHeight());
        } catch (Exception ignored) {}

        player = controladorJuego.getNaveJugador();
        
        boolean evoActiva = controladorJuego.isNaveEvoActiva();
        boolean herido = player.estaHerido();
        
        BufferedImage spriteActual;
        
        if (evoActiva) {
            if (herido) {
                spriteActual = SpritePlayerHeridoEvo1;
            } else {
                spriteActual = usarSpriteplayer ? SpritePlayerEvo1 : SpritePlayerEvo2;
            }
        } else {
            if (herido) {
                spriteActual = spritePlayerHerido1;
            } else {
                spriteActual = usarSpriteplayer ? spritePlayer1 : spritePlayer2;
            }
        }
        
        g.drawImage(spriteActual,
        player.getX(), player.getY(),
        player.getWidth(), player.getHeight(),
        null);



        List<misil> misilesEnemigosCopia = getMisilesEnemigosCopia();
        for (misil m : misilesEnemigosCopia) {
            g.drawImage(mslSprite_Enemigorojo, m.getX(), m.getY(), 30, 30, null);
        }
        
        List<misil> misilesJugadorCopia = new ArrayList<>(controladorJuego.getMisilesJugador());
        for (misil m : misilesJugadorCopia) {
            g.drawImage(misilSprite, m.getX(), m.getY(), 40, 40, null);
        }

        List<personaje_base> enemigoscopia = new ArrayList<>(controladorJuego.getEnemigos());
for (personaje_base enemy : enemigoscopia) {

    if (enemy instanceof Enemigo_Fantasma fantasma) {

        BufferedImage spriteFantasma = null;

        boolean visible  = fantasma.isVisibleFantasma();
        boolean heridoF  = fantasma.estaHerido();

        if (heridoF) {
          
            spriteFantasma = visible ? fantasmaHeridoVisible : fantasmaHeridoInvisible;
        } else {
            
            if (visible) {
                spriteFantasma = fantasmaVisibleFrames[frameFantasma];
            } else {
               
                spriteFantasma = fantasmaInvisibleFrames[frameFantasma];
            }
        }

        if (spriteFantasma != null) {
            g.drawImage(
                spriteFantasma,
                fantasma.getX(), fantasma.getY(),
                fantasma.getWidth(), fantasma.getHeight(),
                null
            );
        } else {
            g.setColor(Color.MAGENTA);
            g.fillRect(
                fantasma.getX(), fantasma.getY(),
                fantasma.getWidth(), fantasma.getHeight()
            );
        }

        continue;   
}
    String tipoEnemigo = enemy.getTipoEnemigo();
    BufferedImage spriteEnemigoActual = null;

    if (enemy.estaHerido()) {
        if ("rojo".equalsIgnoreCase(tipoEnemigo)) {
        spriteEnemigoActual = enemySpriteHerido_Ene_rojo;
    } else if ("violeta".equalsIgnoreCase(tipoEnemigo)) {
        spriteEnemigoActual = enemySpriteHerido_Ene_violeta;
    }
} else {
    if ("rojo".equalsIgnoreCase(tipoEnemigo)) {
        spriteEnemigoActual = usarSpriteenemie ? enemySprite : enemySprite2;
    } else if ("violeta".equalsIgnoreCase(tipoEnemigo)) {
    spriteEnemigoActual = enemySpriteVioleta;
    }
}



    if (spriteEnemigoActual != null) {
        g.drawImage(spriteEnemigoActual,
                    enemy.getX(), enemy.getY(),
                    enemy.getWidth(), enemy.getHeight(), null);
    } else {
        g.setColor(Color.MAGENTA);
        g.fillRect(enemy.getX(), enemy.getY(),
                   enemy.getWidth(), enemy.getHeight());
    }
}


        // Power ups
        if (controladorJuego.isVidaActive()) {
            if (this.vidaExtra != null) {
                g.drawImage(this.vidaExtra,
                            controladorJuego.getVidaX(),
                            controladorJuego.getVidaY(),
                            controladorJuego.getPowerUpSize(),
                            controladorJuego.getPowerUpSize(), null);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(controladorJuego.getVidaX(), controladorJuego.getVidaY(),
                           controladorJuego.getPowerUpSize(), controladorJuego.getPowerUpSize());
            }
        }

        if (controladorJuego.isMisilActive()) {
            if (this.misilx3 != null) {
                g.drawImage(this.misilx3,
                            controladorJuego.getMisilX(),
                            controladorJuego.getMisilY(),
                            controladorJuego.getPowerUpSize(),
                            controladorJuego.getPowerUpSize(), null);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(controladorJuego.getMisilX(), controladorJuego.getMisilY(),
                           controladorJuego.getPowerUpSize(), controladorJuego.getPowerUpSize());
            }
        }

        // Vidas
        if (vidas_img != null) {
            int vida = player.getLife();
            int xInicial = 30;
            int yPos = 30;
            int anchoIcono = 40;
            int altoIcono = 40;
            int espaciado = 10;

            for (int i = 0; i < vida; i++) {
                g.drawImage(vidas_img,
                            xInicial + (i * (anchoIcono + espaciado)),
                            yPos, anchoIcono, altoIcono, null);
            }
        }

        // Puntaje
        g.setColor(Color.PINK);
        g.setFont(new Font("Impact", Font.BOLD, 50));
        g.drawString("PUNTOS  " + controladorJuego.getPuntaje(), 660, 50);

        // Contador de enemigos para evolución
        int kills = controladorJuego.getEnemigosEliminados();
        int objetivo = controladorJuego.getObjetivoEvolucion();
        g.setFont(new Font("Impact", Font.BOLD, 30));
        g.drawString("EVO: " + kills + " / " + objetivo, 1080, 50);

        
        if (controladorJuego.isBuffMisilX3Activo()) {
            int segRest = controladorJuego.getSegundosRestantesMisilX3();
            g.setColor(Color.CYAN);
            g.setFont(new Font("Impact", Font.BOLD, 30));
            g.drawString("MISIL X3: " + segRest + "s", 660, 90);
        }

        if (controladorJuego.isNaveEvoActiva()) {
            int segEvo = controladorJuego.getSegundosRestantesNaveEvo();
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Impact", Font.BOLD, 30));
            g.drawString("NAVE EVO: " + segEvo + "s", 660, 130); 
            }
    }

    private void loadSprites() {
        try {
            this.spritePlayer1 = ImageIO.read(new File("nave/nave1.png"));
            this.spritePlayer2 = ImageIO.read(new File("nave/nave2.png"));
            this.spritePlayerHerido1 = ImageIO.read(new File("nave/nave2.daniada.png"));
            this.SpritePlayerEvo1 = ImageIO.read(new File("nave/naveEvo/nave2.1.png"));
            this.SpritePlayerEvo2 = ImageIO.read(new File("nave/naveEvo/nave2.2.png"));
            this.SpritePlayerHeridoEvo1 = ImageIO.read(new File("nave/naveEvo/nave2.1daniada.png"));
            this.vidas_img = ImageIO.read(new File("img_varias/vidas.png"));
            this.enemySprite = ImageIO.read(new File("Enemigo_rojo/Enemigo_rojo_1.png"));
            this.enemySprite2 = ImageIO.read(new File("Enemigo_rojo/Enemigo_rojo_2.png"));
            this.enemySpriteHerido_Ene_rojo = ImageIO.read(new File("Enemigo_rojo/Enemigo_rojo_dañado.png"));
            this.enemySpriteVioleta = ImageIO.read(new File("Enemigo_violeta/Enemigo_violeta_1.png"));
            this.enemySpriteHerido_Ene_violeta = ImageIO.read(new File("Enemigo_violeta/Enemigo_violeta_dañado.png"));
            this.misilSprite = ImageIO.read(new File("img_varias/misil.png"));
            this.mslSprite_Enemigorojo = ImageIO.read(new File("img_varias/misilEnemigoRojo.png"));
            this.vidaExtra = ImageIO.read(new File("img_varias/vidaExtra.png"));
            this.misilx3 = ImageIO.read(new File("img_varias/misilx3.png"));
            fantasmaVisibleFrames[0]   = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaV1.png"));
            fantasmaVisibleFrames[1] = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaV2.png"));
            fantasmaVisibleFrames[2] = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaV3.png"));
            fantasmaInvisibleFrames[0] = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaI1.png"));
            fantasmaInvisibleFrames[1] = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaI2.png"));
            fantasmaInvisibleFrames[2] = ImageIO.read(new File("Enemigo_fantasma/SpriteFantasmaI3.png"));
            this.fantasmaHeridoVisible = ImageIO.read(new File("Enemigo_fantasma/SpriteDaniadoFantasmaV.png"));
            this.fantasmaHeridoInvisible = ImageIO.read(new File("Enemigo_fantasma/SpriteDaniadoFantasmaI.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ====== PAUSA UI ======
    private static javax.swing.JButton makeImgBtnScaled(String path, int w, int h) {
        javax.swing.ImageIcon src = new javax.swing.ImageIcon(path);
        java.awt.Image img = src.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        javax.swing.ImageIcon scaled = new javax.swing.ImageIcon(img);
        javax.swing.JButton b = new javax.swing.JButton(scaled);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new java.awt.Dimension(w, h));
        b.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return b;
    }

    private void buildPausaUI() {
        pausaOverlay = new javax.swing.JPanel() {
            @Override protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.setColor(new java.awt.Color(0,0,0,160));
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };
        pausaOverlay.setOpaque(false);
        pausaOverlay.setLayout(new java.awt.GridBagLayout());
        pausaOverlay.setVisible(false);

        javax.swing.JPanel box = new javax.swing.JPanel();
        box.setOpaque(false);
        box.setLayout(new javax.swing.BoxLayout(box, javax.swing.BoxLayout.Y_AXIS));

        JButton btnReanudar = makeImgBtnScaled("Img_varias/reanudar.png", 200, 70);
        JButton btnMenuPrinc = makeImgBtnScaled("Img_varias/menuprincipal.png", 200, 70);

        box.add(javax.swing.Box.createVerticalStrut(12)); box.add(btnReanudar);
        box.add(javax.swing.Box.createVerticalStrut(12)); box.add(btnMenuPrinc);

        btnReanudar.addActionListener(e -> {
        AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
        setPaused(false);
        });
        btnMenuPrinc.addActionListener(e -> { 
            AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
            if (onExitToMenu != null) onExitToMenu.run(); 
        });

        pausaOverlay.add(box, new java.awt.GridBagConstraints());
        add(pausaOverlay, java.awt.BorderLayout.CENTER);
    }

    private void bindTeclaPausa() {
        javax.swing.InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        javax.swing.ActionMap am = getActionMap();

        im.put(javax.swing.KeyStroke.getKeyStroke("P"), "togglePausa");
        am.put("togglePausa", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setPaused(!pausa);
            }
        });
    }

    // ====== PAUSA / REINICIO (SIN HILOS) ======
    public void setPaused(boolean p) {
        //if (p == pausa) return;
        pausa = p;
        if (pausaOverlay != null) pausaOverlay.setVisible(pausa);

        if (pausa) {
            AudioManager.getInstancia().pauseMusica();
            if (controladorJuego != null) controladorJuego.detenerPowerUps();
        } else {
            AudioManager.getInstancia().resumeMusica();
            if (controladorJuego != null) configurarPowerUps(7, 15);
        }
        revalidate();
        repaint();
    }

    public void reiniciarJuego() {
        // Detener power-ups anteriores
        if (controladorJuego != null) {
            controladorJuego.detenerPowerUps();
        }

        // salir de pausa internamente
        pausa = false;
        if (pausaOverlay != null) pausaOverlay.setVisible(false);

        // Restablecer jugador
        this.player = new spaceship_1(80, 270, 150, 150, 5, 12, 12, false);

        // Limpiar listas
        this.misilesEnemigos.clear();
        this.enemies.clear();

        // Nuevo controlador con estado limpio
        this.controladorJuego = new ControladorJuego(player, this.enemies);

        // Reset variables de animación
        this.contadorFramesEnemigos = 0;
        this.usarSpriteplayer = true;

        this.gameOverNotificado = false;

        this.requestFocusInWindow();
    }

    public void setOnGameOver(java.util.function.IntConsumer onGameOver) {
        this.onGameOver = onGameOver;
    }

}