package presentation;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import presentation.FondoAnimado;
import presentation.ReproductorMusica;
import presentation.SoundEffect;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class PoobkemonGUI1 extends JFrame {
    private FondoAnimado fondo;
    private String rutaFondo = "Poobkemon/mult/pokemonIntro.gif";
    private String rutaEffectAudio = "Poobkemon/mult/button_click.wav";
    private SoundEffect buttonSound;
    private ReproductorMusica reproductor;
    private String font = "Times New Roman";
    

    public PoobkemonGUI1() {
        super("Poobkemon Garcia-Romero");
        prepareElementsDimension();
        prepareSounds();
        prepareBackground();
        preprareMenu();
        prepareButtons();
        setupWindowListeners();
    }


    private void prepareElementsDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = screenSize.width/2 + 50;
        int windowHeight = screenSize.height/2 + 30;
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
    }

    private void prepareSounds() {
        buttonSound = new SoundEffect(rutaEffectAudio);
        buttonSound.setVolume(80); // Ajusta el volumen si es necesario
        reproductor = new ReproductorMusica(rutaEffectAudio);
        reproductor.setVolume(70); // Ajusta el volumen al 50%
    }

    private void prepareBackground(){
        fondo = new FondoAnimado(rutaFondo);
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
        
    }

    private void preprareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Options");
        JMenuItem music = new JMenuItem("Pause Music");

        //Pone en pausa la música del juego
        music.addActionListener(e -> {
            if (reproductor.estaReproduciendo()) {
                reproductor.detener();
                music.setText("Play Music");
            } else {
                reproductor.reproducir();
                music.setText("Pause Music");
            }
        });

        menuBar.add(fileMenu);
        fileMenu.add(music);

        
        setJMenuBar(menuBar);
    }
    
    private void prepareButtons() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(3, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(150, 270, 10, 270));
    
        // Paleta de colores corregida
        Color buttonColor = new Color(35, 120, 65);
        Color hoverColor = new Color(70, 160, 90);
        Color pressedColor = new Color(20, 90, 50);
        Color textColor = new Color(255, 255, 200);
    
        // Borde corregido - especificamos grosor (1 pixel)
        Border compoundBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 160, 120), 1), // Grosor añadido aquí
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        );
    
        // Botón Play
        JButton startButton = new JButton("Play") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(pressedColor);
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(buttonColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                if (!getModel().isPressed()) {
                    g2.setColor(new Color(255, 255, 255, 50));
                    g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/2, 8, 8);
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        // Configuración del botón (sin cambios)
        startButton.setFont(new Font(font, Font.BOLD, 16)); // Cambiado a Arial por si no tienes la fuente Pokémon
        startButton.setForeground(textColor);
        startButton.setBorder(compoundBorder);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> start());
    
        // Botón Exit (mismo estilo)
        JButton exitButton = new JButton("Exit") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(pressedColor);
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(buttonColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                if (!getModel().isPressed()) {
                    g2.setColor(new Color(255, 255, 255, 50));
                    g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/2, 8, 8);
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        exitButton.setFont(new Font(font, Font.BOLD, 16));
        exitButton.setForeground(textColor);
        exitButton.setBorder(compoundBorder);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> exitApplication());
    
        panel.add(startButton);
        panel.add(exitButton);
        fondo.add(panel, BorderLayout.SOUTH);
    }

    private void setupWindowListeners() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }

    


}