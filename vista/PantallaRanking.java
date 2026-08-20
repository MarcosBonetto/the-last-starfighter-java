package vista;

import modelo.JugadorBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controlador.AudioManager;

public class PantallaRanking extends JPanel {
    private final JTextArea areaRanking;
    private final FondoAnimado fondo = new FondoAnimado(); //mismo fondo que el panel

    public PantallaRanking(vista.Ventanas ventana) {
        setLayout(null);

        //titulo
        JLabel titulo = new JLabel("🏆 TOP 5 JUGADORES 🏆", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 50, 1920, 60);
        add(titulo);

        //area de ranking
        areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        areaRanking.setOpaque(false); //fondo transparente
        areaRanking.setForeground(Color.WHITE);
        areaRanking.setFont(new Font("Consolas", Font.BOLD, 28));

        JScrollPane scroll = new JScrollPane(areaRanking);
        scroll.setBounds(650, 180, 600, 400);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false); //scroll transparente también
        add(scroll);

        //Botón Volver
        JButton volver = new JButton("VOLVER");
        volver.setBounds(850, 650, 200, 60);
        volver.setFont(new Font("Arial", Font.BOLD, 22));
        volver.addActionListener(e -> {
        AudioManager.getInstancia().reproducirFX("sonidos/ClickBoton.wav");
        ventana.mostrarPantalla("INICIO");
        });
        add(volver);

        //Animación del fondo (mismo bucle que en panel)
        javax.swing.Timer fondoLoop = new javax.swing.Timer(16, e -> {
            fondo.actualizarOffsets();
            repaint();
        });
        fondoLoop.start();
    }
    
    public void actualizarRanking(List<JugadorBD> top5) {
        StringBuilder sb = new StringBuilder();
        int pos = 1;
        for (JugadorBD j : top5) {
        sb.append(pos++).append(". ")
          .append(j.getNombre())
          .append(" - ")
          .append(j.getPuntaje())
          .append(" pts\n");
    }
    areaRanking.setText(sb.toString());
}

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        fondo.render(g, getWidth(), getHeight()); //dibuja el fondo animado
    }
}
