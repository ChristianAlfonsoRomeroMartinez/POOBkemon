package presentation;

import domain.Poobkemon;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLabelUI;

public class PoobkemonGUI extends JFrame {
    private FondoAnimado fondo;
    private String rutaImagen = "Poobkemon/mult/fondo2.jpeg";
    private String rutaImagenBattle = "Poobkemon/mult/fondo3.jpeg";
    private String font = "Times New Roman";
    private String rutaEffectAudio = "Poobkemon/mult/button_click.wav";
    private ReproductorMusica reproductor;
    private SoundEffect buttonSound;
    private Map<String, List<String>> playerItems = new HashMap<>();
    private Map<String, List<String>> selectedMoves = new HashMap<>();
    private boolean isPlayer1Turn = true;
    private List<String> player1Pokemon;
    private List<String> player2Pokemon;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private Timer turnTimer; // Temporizador para el turno
    private int turnTimeRemaining = 20; // Tiempo restante en segundos
    private JLabel turnTimerLabel; // Etiqueta para mostrar el tiempo restante
    private List<JButton> player1BattleMoveButtons = new ArrayList<>();
    private List<JButton> player2BattleMoveButtons = new ArrayList<>();
    private List<JButton> player1BattlePokeballButtons = new ArrayList<>();
    private List<JButton> player2BattlePokeballButtons = new ArrayList<>();

    public PoobkemonGUI() {
        super("Poobkemon Garcia-Romero");
        prepareElementsDimension();
        prepareSounds();
        prepareBackground();
        preprareMenu();
        prepareButtons();
        setupWindowListeners();
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

    private void prepareSounds() {
        buttonSound = new SoundEffect(rutaEffectAudio);
        buttonSound.setVolume(80); // Ajusta el volumen si es necesario
        reproductor = new ReproductorMusica("Poobkemon/mult/musicaIntro.wav");
        reproductor.setVolume(70); // Ajusta el volumen al 50%
    }

    private void prepareBackground(){
        fondo = new FondoAnimado("Poobkemon/mult/pokemonIntro.gif");
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
        
    }


    private void prepareElementsDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = screenSize.width/2 + 50;
        int windowHeight = screenSize.height/2 + 30;
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
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

    // Clase para dibujar el fondo animado y escalarlo
    class FondoAnimado extends JPanel {
        private final ImageIcon gif;
        private Image imagenFija; // Para la segunda pantalla
    
        public FondoAnimado(String rutaGIF) {
            gif = new ImageIcon(rutaGIF);
        }
    
        // Método para cambiar a imagen fija
        public void setImagenFija(String rutaImagen) {
            this.imagenFija = new ImageIcon(rutaImagen).getImage();
            repaint();
        }
    
        // Método para volver al GIF animado
        public void resetToGif() {
            this.imagenFija = null;
            repaint();
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFija != null) {
                // Dibuja la imagen fija
                g.drawImage(imagenFija, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Dibuja el GIF animado
                g.drawImage(gif.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Clase que reproduce un archivo WAV en bucle
    public class ReproductorMusica {
    private Clip clip;
    private boolean estaReproduciendo = true;
    private FloatControl gainControl; // Control de volumen

    public ReproductorMusica(String rutaArchivo) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Obtener el control de volumen (si está disponible)
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(50); // Volumen al 50% por defecto
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir música: " + e.getMessage());
        }
    }

    /**
     * Ajusta el volumen (porcentaje de 0 a 100).
     * @param percent : 0 = mudo, 100 = máximo.
     */
    public void setVolume(int percent) {
        if (gainControl == null) return; // Si no hay control de volumen, salir

        // Convertir porcentaje a decibeles (rango típico: -80dB a 6dB)
        float min = gainControl.getMinimum();
        float max = gainControl.getMaximum();
        float range = max - min;
        float gain = (range * percent / 100.0f) + min;

        gainControl.setValue(gain);
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            estaReproduciendo = false;
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.start();
            estaReproduciendo = true;
        }
    }

    public boolean estaReproduciendo() {
        return estaReproduciendo;
    }
}

public class SoundEffect {
    private Clip clip;

    public SoundEffect(String soundPath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundPath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al cargar el efecto de sonido: " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Rebobinar al inicio
            clip.start();
        }
    }

    // Opcional: Ajustar volumen (similar a ReproductorMusica)
    public void setVolume(float percent) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * percent / 100.0f) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}


    private void start() {
        // Cambia a imagen fija para la segunda pantalla
        fondo.setImagenFija(rutaImagen); // Cambia por tu ruta de imagen
        
        // Limpia el contenido actual
        getContentPane().removeAll();
        setContentPane(fondo); // Vuelve a establecer el fondo como content pane
        
        // Resto del código de start() se mantiene igual...
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- Panel izquierdo (botones) ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 15, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));

        Font buttonFont = new Font(font, Font.BOLD, 18);
            
        // Botones
        if (!false) {
            JButton continueBtn = createMenuButton("CONTINUE", buttonFont, true, 220,55);
            continueBtn.addActionListener(e -> continueGame());
            buttonPanel.add(continueBtn);
        }

        JButton newGameBtn = createMenuButton("NEW GAME", buttonFont, true,220,55);
        JButton oldGameBtn = createMenuButton("OLD GAME", buttonFont, true,220,55);
        JButton scoreBtn = createMenuButton("SCORE", buttonFont, true,220,55);
            
        newGameBtn.addActionListener(e -> startNewGame());
        oldGameBtn.addActionListener(e -> {});
        scoreBtn.addActionListener(e -> {});
        
            
        buttonPanel.add(newGameBtn);
        buttonPanel.add(oldGameBtn);
        buttonPanel.add(scoreBtn);
            
        
        
        
            
        // --- Botón BACK abajo ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false); // Esto es esencial para la transparencia
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton backBtn = createMenuButton("BACK", buttonFont, true,200,55);
        backBtn.addActionListener(e -> returnToGame());
        
        // Centrar el botón BACK
        JPanel backBtnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backBtnContainer.setOpaque(false); // Esto es esencial para la transparencia
        backBtnContainer.add(backBtn);
        bottomPanel.add(backBtnContainer, BorderLayout.EAST);
            
        // --- Ensamblar componentes ---
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
            
        fondo.add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
}

private JButton createMenuButton(String text, Font font, boolean enabled, int width, int height) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // --- Fondo con gradiente y esquinas redondeadas ---
            if (isEnabled()) {
                // Gradiente principal (verde oscuro a verde claro)
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(50, 120, 70, 180),
                    0, getHeight(), new Color(65, 150, 85)
                );
                g2.setPaint(gp);
                
                // Sombra suave debajo del botón
                //g2.setColor(new Color(0, 0, 0, 50));
                //g2.fillRoundRect(1, 3, getWidth()-2, getHeight()-2, 25, 25);
            } else {
                // Fondo deshabilitado (gris con transparencia)
                g2.setColor(new Color(70, 70, 70, 150));
            }
            
            // Relleno del botón
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            
            // --- Borde con efecto de resaltado ---
            if (isEnabled()) {
                // Borde exterior brillante (solo si está habilitado)
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(new Color(180, 255, 180, 120));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            }

            // --- Texto centrado con sombra ---
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            // Sombra del texto (sutil)
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(getText(), x + 1, y + 1);
            
            // Texto principal
            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No pintar borde por defecto
        }
    };

    // --- Configuración base del botón ---
    button.setFont(font.deriveFont(Font.BOLD)); // Texto en negrita
    button.setEnabled(enabled);
    button.setPreferredSize(new Dimension(width, height)); // Tamaño ligeramente más grande
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));

    // --- Efectos de hover y pulsación ---
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (button.isEnabled()) {
                button.setForeground(new Color(255, 230, 150)); // Texto dorado claro
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (button.isEnabled()) {
                button.setForeground(Color.WHITE);
                button.setCursor(Cursor.getDefaultCursor());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (button.isEnabled()) {
                // Efecto de "presionado" (mueve ligeramente el texto)
                buttonSound.play();
                button.setBorder(BorderFactory.createEmptyBorder(6, 25, 4, 25));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            button.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
        }
    });

    return button;
}

    // Métodos de ejemplo para las acciones
    private void continueGame() {
        System.out.println("Continuar partida");
    }

    private void startNewGame() {
        // Restaurar el fondo animado original
        fondo.setImagenFija(rutaImagen);
        
        // Limpiar el contenido actual
        getContentPane().removeAll();
        setContentPane(fondo); // Vuelve a establecer el fondo animado
        
        // Mostrar directamente la pantalla de tipo de juego
        showGameTypeSelectionFirst();
    }
    
    private void showGameTypeSelectionFirst() {
        // 1. Establecer la imagen de fondo (la misma que en el menú principal)
        fondo.setImagenFija(rutaImagen);
        getContentPane().removeAll();
        setContentPane(fondo);
    
        // 2. Crear el panel principal (transparente)
        JPanel typeGamePanel = new JPanel(new BorderLayout());
        typeGamePanel.setOpaque(false);
        typeGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // 3. Título con estilo mejorado
        JLabel titleLabel = new JLabel("SELECT GAME TYPE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font(font, Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
    
        // 4. Panel de contenido (transparente)
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);
    
        // 5. Paneles de imágenes
        JPanel leftImagesPanel = createImagePanel("human.png", "human.png", "porygon.png");
        leftImagesPanel.setOpaque(false);
        leftImagesPanel.setPreferredSize(new Dimension(getWidth()/6, getHeight()));
    
        JPanel rightImagesPanel = createImagePanel("human.png", "porygon.png", "porygon.png");
        rightImagesPanel.setOpaque(false);
        rightImagesPanel.setPreferredSize(new Dimension(getWidth()/6, getHeight()));
    
        // 6. Panel de botones (transparente)
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
    
        // 7. Botones
        JButton humanVsHumanBtn = createMenuButton("HUMAN vs HUMAN", buttonFont, true, 220, 55);
        humanVsHumanBtn.addActionListener(e -> {
            buttonSound.play();
            // Al hacer clic en HUMAN vs HUMAN, mostrar la pantalla de selección de modo
            showGameModeSelection();
        });
    
        JButton humanVsMachineBtn = createMenuButton("HUMAN vs MACHINE", buttonFont, true, 220, 55);
        humanVsMachineBtn.addActionListener(e -> {
            buttonSound.play();
            getContentPane().removeAll();
            setContentPane(new MachineGameTypeSelectionScreen(this)); // Mantener la funcionalidad original
            revalidate();
            repaint();
        });
    
        JButton machineVsMachineBtn = createMenuButton("MACHINE vs MACHINE", buttonFont, true, 220, 55);
        machineVsMachineBtn.addActionListener(e -> {
            System.out.println("Machine vs Machine selected");
        });
    
        buttonPanel.add(humanVsHumanBtn);
        buttonPanel.add(humanVsMachineBtn);
        buttonPanel.add(machineVsMachineBtn);
    
        // 8. Ensamblar componentes
        contentPanel.add(leftImagesPanel, BorderLayout.WEST);
        contentPanel.add(rightImagesPanel, BorderLayout.EAST);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
    
        // 9. Botón BACK (derecha inferior)
        JButton backBtn = createMenuButton("BACK", buttonFont, true, 200, 55);
        backBtn.addActionListener(e -> start()); // Volver al menú principal
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backBtn);
    
        // 10. Agregar todo al panel principal
        typeGamePanel.add(titleLabel, BorderLayout.NORTH);
        typeGamePanel.add(contentPanel, BorderLayout.CENTER);
        typeGamePanel.add(bottomPanel, BorderLayout.SOUTH);
    
        // 11. Agregar al contenedor principal
        fondo.add(typeGamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void showGameModeSelection() {
        // Restaurar el fondo animado original
        fondo.setImagenFija(rutaImagen);
        
        // Limpiar el contenido actual
        getContentPane().removeAll();
        setContentPane(fondo); // Vuelve a establecer el fondo animado
        
        // Crear el panel de selección de modo (con fondo transparente)
        JPanel modeSelectionPanel = new JPanel(new BorderLayout());
        modeSelectionPanel.setOpaque(false); // ¡Importante! Para que se vea el fondo
        modeSelectionPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    
        // Título (con sombra para mejor legibilidad)
        JLabel titleLabel = new JLabel("SELECT GAME MODE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font(font, Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Efecto de sombra en el texto
        titleLabel.setUI(new BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2.setColor(new Color(0, 0, 0, 150));
                g2.drawString(titleLabel.getText(), 3, 23);
                
                // Texto principal
                g2.setColor(Color.YELLOW);
                g2.drawString(titleLabel.getText(), 0, 20);
                
                g2.dispose();
            }
        });
    
        // Panel de botones (transparente)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
    
        Font buttonFont = new Font(font, Font.BOLD, 20);
    
        // Botón para modo Normal
        JButton normalModeBtn = createMenuButton("NORMAL MODE", buttonFont, true, 300, 55);
        normalModeBtn.addActionListener(e -> showPokemonSelectionScreen()); // Ir a selección de Pokémon
    
        // Botón para modo Survival
        JButton survivalModeBtn = createMenuButton("SURVIVAL MODE", buttonFont, true, 300, 55);
        survivalModeBtn.addActionListener(e -> {
            try {
                // Iniciar directamente una batalla en modo supervivencia
                Poobkemon poobkemon = new Poobkemon();
                poobkemon.startBattleSurvival("Player 1", "Player 2");
                
                // Obtener Pokémon aleatorios
                List<String> allPokemon = Poobkemon.getAvailablePokemon();
                Collections.shuffle(allPokemon);
                List<String> player1Pokemon = new ArrayList<>(allPokemon.subList(0, 6));
                List<String> player2Pokemon = new ArrayList<>(allPokemon.subList(6, 12));
                
                // Mostrar la pantalla de batalla
                showBattleScreen(player1Pokemon, player2Pokemon);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al iniciar la batalla: " + ex.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        buttonPanel.add(normalModeBtn);
        buttonPanel.add(survivalModeBtn);
    
        // Botón Back (alineado a la derecha)
        JButton backBtn = createMenuButton("BACK", buttonFont, true, 200, 55);
        backBtn.addActionListener(e -> showGameTypeSelectionFirst()); // Volver a la pantalla de tipo de juego
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backBtn);
    
        // Ensamblar componentes
        modeSelectionPanel.add(titleLabel, BorderLayout.NORTH);
        modeSelectionPanel.add(buttonPanel, BorderLayout.CENTER);
        modeSelectionPanel.add(bottomPanel, BorderLayout.SOUTH);
    
        // Agregar al fondo animado
        fondo.add(modeSelectionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void showGameTypeSelection(String gameMode) {
        // 1. Establecer la imagen de fondo (la misma que en el menú principal)
        fondo.setImagenFija(rutaImagen); // Asegúrate que esta ruta sea correcta
        getContentPane().removeAll();
        setContentPane(fondo); // Esto es clave para que se vea el fondo
    
        // 2. Crear el panel principal (transparente)
        JPanel typeGamePanel = new JPanel(new BorderLayout());
        typeGamePanel.setOpaque(false); // ¡Importante! Para que se vea el fondo
        typeGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // 3. Título con estilo mejorado (para que contraste con el fondo)
        JLabel titleLabel = new JLabel("GAME TYPE (" + gameMode + " MODE)", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font(font, Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
    
        // 4. Panel de contenido (transparente)
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);
    
        // 5. Paneles de imágenes (ajustados para el nuevo fondo)
        JPanel leftImagesPanel = createImagePanel("human.png", "human.png", "porygon.png");
        leftImagesPanel.setOpaque(false);
        leftImagesPanel.setPreferredSize(new Dimension(getWidth()/6, getHeight()));
    
        JPanel rightImagesPanel = createImagePanel("human.png", "porygon.png", "porygon.png");
        rightImagesPanel.setOpaque(false);
        rightImagesPanel.setPreferredSize(new Dimension(getWidth()/6, getHeight()));
    
        // 6. Panel de botones (transparente)
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
    
        // 7. Botones (usarán tu estilo createMenuButton)
        JButton humanVsHumanBtn = createMenuButton("HUMAN vs HUMAN", buttonFont, true,220,55);
        humanVsHumanBtn.addActionListener(e -> {
            buttonSound.play();
            if ("SURVIVAL".equals(gameMode)) {
                try {
                    Poobkemon poobkemon = new Poobkemon();
                    // Iniciar una batalla en modo supervivencia
                    poobkemon.startBattleSurvival("Player 1", "Player 2");
                    
                    // Obtener la lista de todos los Pokémon disponibles
                    List<String> allPokemon = Poobkemon.getAvailablePokemon();
                    
                    // Aleatorizar la lista
                    Collections.shuffle(allPokemon);
                    
                    // Seleccionar 6 Pokémon para cada jugador
                    List<String> player1Pokemon = new ArrayList<>(allPokemon.subList(0, 6));
                    List<String> player2Pokemon = new ArrayList<>(allPokemon.subList(6, 12));
                    
                    // Mostrar la pantalla de batalla
                    showBattleScreen(player1Pokemon, player2Pokemon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al iniciar la batalla: " + ex.getMessage(), 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // En modo NORMAL, mostrar la pantalla de selección de Pokémon
                showPokemonSelectionScreen();
            }
        });
    
        JButton humanVsMachineBtn = createMenuButton("HUMAN vs MACHINE", buttonFont, true, 220, 55);
        humanVsMachineBtn.addActionListener(e -> {
            buttonSound.play();
            getContentPane().removeAll();
            setContentPane(new MachineGameTypeSelectionScreen(this)); // Abre la nueva pantalla
            revalidate();
            repaint();
        });
    
        JButton machineVsMachineBtn = createMenuButton("MACHINE vs MACHINE", buttonFont, true,220,55);
        machineVsMachineBtn.addActionListener(e -> {
            System.out.println(gameMode + " mode - Machine vs Machine selected");
        });
    
        buttonPanel.add(humanVsHumanBtn);
        buttonPanel.add(humanVsMachineBtn);
        buttonPanel.add(machineVsMachineBtn);
    
        // 8. Ensamblar componentes
        contentPanel.add(leftImagesPanel, BorderLayout.WEST);
        contentPanel.add(rightImagesPanel, BorderLayout.EAST);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
    
        // 9. Botón BACK (derecha inferior)
        JButton backBtn = createMenuButton("BACK", buttonFont, true,200,55);
        backBtn.addActionListener(e -> startNewGame());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backBtn);
    
        // 10. Agregar todo al panel principal
        typeGamePanel.add(titleLabel, BorderLayout.NORTH);
        typeGamePanel.add(contentPanel, BorderLayout.CENTER);
        typeGamePanel.add(bottomPanel, BorderLayout.SOUTH);
    
        // 11. Agregar al contenedor principal
        fondo.add(typeGamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showPokemonSelectionScreen() {
    // Limpiar el contenido actual
    getContentPane().removeAll();
    setContentPane(fondo); // Mantener el fondo actual
    fondo.setImagenFija(rutaImagen); // Cambiar a la imagen fija

    // Panel principal
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setOpaque(false);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20)); // Márgenes más grandes para mejor separación

    // Título de la pantalla
    JLabel titleLabel = new JLabel("Seleccionen los Pokémon", SwingConstants.CENTER);
    titleLabel.setFont(new Font(font, Font.BOLD, 24));
    titleLabel.setForeground(Color.YELLOW);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Separación inferior del título

    // Panel de selección de Pokémon para ambos jugadores
    JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 30, 0)); // Espaciado horizontal entre jugadores
    selectionPanel.setOpaque(false);
    selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes internos del panel de selección

    // Panel del jugador 1
    JPanel player1Panel = createPlayerSelectionPanel("Player 1");
    // Panel del jugador 2
    JPanel player2Panel = createPlayerSelectionPanel("Player 2");

    selectionPanel.add(player1Panel);
    selectionPanel.add(player2Panel);

    // Botón NEXT
    JButton nextButton = createMenuButton("NEXT", new Font(font, Font.BOLD, 18), true, 200, 50);
    nextButton.addActionListener(e -> {
        JTextField player1NameField = (JTextField) player1Panel.getClientProperty("nameField");
        JTextField player2NameField = (JTextField) player2Panel.getClientProperty("nameField");

        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();

        List<String> selectedPlayer1 = getSelectedPokemon(player1Panel);
        List<String> selectedPlayer2 = getSelectedPokemon(player2Panel);

        List<String> player1Items = playerItems.get("Player 1");
        List<String> player2Items = playerItems.get("Player 2");

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben ingresar su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedPlayer1.size() > 6 || selectedPlayer2.size() > 6) {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben seleccionar exactamente 6 Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if (selectedPlayer1.isEmpty() || selectedPlayer2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben seleccionar al menos un Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        System.out.println(player1Name + " seleccionó Pokémon: " + selectedPlayer1 + " e ítems: " + player1Items);
        System.out.println(player2Name + " seleccionó Pokémon: " + selectedPlayer2 + " e ítems: " + player2Items);

        showPokemonSummaryScreen(selectedPlayer1, selectedPlayer2);

        // Aquí puedes pasar las listas seleccionadas al dominio o iniciar la batalla
    });

    // Botón ITEMS
    JButton itemsButton = createMenuButton("ITEMS", new Font(font, Font.BOLD, 18), true, 200, 50);
    itemsButton.addActionListener(e -> showItemSelectionScreen());

    // Botón BACK
    JButton backButton = createMenuButton("BACK", new Font(font, Font.BOLD, 18), true, 200, 50);
    backButton.addActionListener(e -> showGameTypeSelection("NORMAL"));

    // Panel inferior con los botones
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Espaciado entre botones
    bottomPanel.setOpaque(false);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Separación superior
    bottomPanel.add(itemsButton);
    bottomPanel.add(nextButton);
    bottomPanel.add(backButton);

    // Ensamblar el panel principal
    mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior
    mainPanel.add(selectionPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    fondo.add(mainPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
}

/**
 * Crea un panel de selección de Pokémon para un jugador.
 */
private JPanel createPlayerSelectionPanel(String playerName) {
    JPanel playerPanel = new JPanel(new BorderLayout());
    playerPanel.setOpaque(false);

    // Campo de texto para el nombre del jugador
    JTextField nameField = new JTextField(playerName);
    nameField.setFont(new Font(font, Font.PLAIN, 17));
    nameField.setHorizontalAlignment(SwingConstants.CENTER);
    nameField.setBackground(new Color(0, 51, 102)); // Fondo azul oscuro
    nameField.setForeground(new Color(204, 255, 204)); // Texto verde claro
    nameField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2)); // Borde azul

    // Lista de Pokémon disponibles
    List<String> availablePokemon = Poobkemon.getAvailablePokemon();
    DefaultListModel<String> pokemonModel = new DefaultListModel<>();
    availablePokemon.forEach(pokemonModel::addElement);

    JList<String> pokemonList = new JList<>(pokemonModel);
    pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    pokemonList.setVisibleRowCount(5);
    pokemonList.setFont(new Font(font, Font.PLAIN, 14));
    pokemonList.setBackground(new Color(0, 102, 153)); // Fondo azul
    pokemonList.setForeground(new Color(204, 255, 204)); // Texto verde claro
    pokemonList.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102))); // Borde azul oscuro

    JScrollPane scrollPane = new JScrollPane(pokemonList);
    scrollPane.setOpaque(false); // Hacer el JScrollPane transparente
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar bordes

    // Botón para agregar Pokémon
    JButton addButton = createMenuButton("ADD", new Font(font, Font.BOLD, 14), true, 100, 40);
    DefaultListModel<String> selectedPokemonModel = new DefaultListModel<>();
    JList<String> selectedPokemonList = new JList<>(selectedPokemonModel);
    selectedPokemonList.setVisibleRowCount(5);
    selectedPokemonList.setFont(new Font(font, Font.PLAIN, 14));
    selectedPokemonList.setBackground(new Color(0, 51, 102)); // Fondo azul oscuro
    selectedPokemonList.setForeground(new Color(204, 255, 204)); // Texto verde claro
    selectedPokemonList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2)); // Borde azul

    JScrollPane selectedScrollPane = new JScrollPane(selectedPokemonList);
    selectedScrollPane.setOpaque(false); // Hacer el JScrollPane transparente
    selectedScrollPane.getViewport().setOpaque(false); // Hacer el Viewport transparente
    selectedScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar bordes

    addButton.addActionListener(e -> {
        String selectedPokemon = pokemonList.getSelectedValue();
        if (selectedPokemon != null && selectedPokemonModel.size() < 6) {
            selectedPokemonModel.addElement(selectedPokemon);
        } else if (selectedPokemonModel.size() >= 6) {
            JOptionPane.showMessageDialog(this, "Solo puedes seleccionar 6 Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Botón para borrar Pokémon
    JButton removeButton = createMenuButton("REMOVE", new Font(font, Font.BOLD, 14), true, 100, 40);
    removeButton.addActionListener(e -> {
        String selectedPokemon = selectedPokemonList.getSelectedValue();
        if (selectedPokemon != null) {
            selectedPokemonModel.removeElement(selectedPokemon);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un Pokémon para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Panel para los botones ADD y REMOVE
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(addButton);
    buttonPanel.add(removeButton);

    // Ensamblar el panel del jugador
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setOpaque(false);
    topPanel.add(nameField, BorderLayout.NORTH);
    topPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setOpaque(false);
    bottomPanel.add(buttonPanel, BorderLayout.NORTH);
    bottomPanel.add(selectedScrollPane, BorderLayout.CENTER);

    playerPanel.add(topPanel, BorderLayout.NORTH);
    playerPanel.add(bottomPanel, BorderLayout.CENTER);

    playerPanel.putClientProperty("nameField", nameField);
    playerPanel.putClientProperty("selectedPokemonList", selectedPokemonList);

    return playerPanel;
}

/**
 * Obtiene los Pokémon seleccionados de un panel de jugador.
 */
private List<String> getSelectedPokemon(JPanel playerPanel) {
    @SuppressWarnings("unchecked")
    JList<String> selectedPokemonList = (JList<String>) playerPanel.getClientProperty("selectedPokemonList");
    List<String> selectedPokemons = new ArrayList<>();
    for (int i = 0; i < selectedPokemonList.getModel().getSize(); i++) {
        selectedPokemons.add(selectedPokemonList.getModel().getElementAt(i));
    }
    return selectedPokemons;
}
    
private List<String> getSelectedItems(JPanel playerPanel) {
    @SuppressWarnings("unchecked")
    JList<String> selectedItemList = (JList<String>) playerPanel.getClientProperty("selectedItemList");
    List<String> selectedItems = new ArrayList<>();
    for (int i = 0; i < selectedItemList.getModel().getSize(); i++) {
        selectedItems.add(selectedItemList.getModel().getElementAt(i));
    }
    return selectedItems;
}
    
    
    // MÉTODO ACTUALIZADO PARA IMÁGENES RESPONSIVAS
    private JPanel createImagePanel(String... imageNames) {
        return new JPanel(new GridLayout(imageNames.length, 1, 0, 20)) {
            private Image[] images = new Image[imageNames.length];
            
            {
                setBackground(Color.BLACK);
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                for (int i = 0; i < imageNames.length; i++) {
                    try {
                        String path = System.getProperty("user.dir") + "/Poobkemon/mult/" + imageNames[i];
                        images[i] = new ImageIcon(path).getImage();
                    } catch (Exception e) {
                        images[i] = null;
                    }
                }
                
                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        repaint();
                    }
                });
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                int cellWidth = getWidth();
                int cellHeight = getHeight() / imageNames.length;
                
                for (int i = 0; i < imageNames.length; i++) {
                    if (images[i] != null) {
                        int originalWidth = images[i].getWidth(null);
                        int originalHeight = images[i].getHeight(null);
                        float aspectRatio = (float) originalWidth / originalHeight;
                        
                        int newWidth = (int) (cellWidth * 0.8);
                        int newHeight = (int) (newWidth / aspectRatio);
                        
                        if (newHeight > cellHeight * 0.8) {
                            newHeight = (int) (cellHeight * 0.8);
                            newWidth = (int) (newHeight * aspectRatio);
                        }
                        
                        int x = (cellWidth - newWidth) / 2;
                        int y = (i * cellHeight) + (cellHeight - newHeight) / 2;
                        
                        g2.drawImage(images[i], x, y, newWidth, newHeight, this);
                    }
                }
            }
        };
    }



    private void returnToGame() {
        getContentPane().removeAll();
        fondo.resetToGif(); // Volver al GIF animado
        prepareButtons();
        revalidate();
        repaint();
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this,"¿Estás seguro que quieres salir de Poobkemon?","Confirmar salida",JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void showItemSelectionScreen() {
    fondo.setImagenFija(rutaImagen);

    getContentPane().removeAll();
    setContentPane(fondo);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setOpaque(false);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

    JLabel titleLabel = new JLabel("Seleccionen los Ítems", SwingConstants.CENTER);
    titleLabel.setFont(new Font(font, Font.BOLD, 24));
    titleLabel.setForeground(Color.YELLOW);
    titleLabel.setBackground(new Color(0,0,0,0));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 30, 0));
    selectionPanel.setOpaque(true);
    selectionPanel.setBackground(new Color(0,0,0,0));
    selectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    JPanel player1Panel = createItemSelectionPanel("");
    JPanel player2Panel = createItemSelectionPanel("");

    selectionPanel.add(player1Panel);
    selectionPanel.add(player2Panel);

    JButton doneButton = createMenuButton("DONE", new Font(font, Font.BOLD, 18), true, 200, 50);
    doneButton.addActionListener(e -> {
        List<String> selectedItemsPlayer1 = getSelectedItems(player1Panel);
        List<String> selectedItemsPlayer2 = getSelectedItems(player2Panel);

        // Validar máximo 6 ítems
        if (selectedItemsPlayer1.size() > 4 || selectedItemsPlayer2.size() > 4) {
            JOptionPane.showMessageDialog(this, "Máximo 6 ítems por jugador", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        playerItems.put("Player 1", selectedItemsPlayer1);
        playerItems.put("Player 2", selectedItemsPlayer2);

        showPokemonSelectionScreen();
    });

    JButton backButton = createMenuButton("BACK", new Font(font, Font.BOLD, 18), true, 200, 50);
    backButton.addActionListener(e -> showPokemonSelectionScreen());

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    bottomPanel.setOpaque(false);
    bottomPanel.setBackground(new Color(0,0,0,0));
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    bottomPanel.add(doneButton);
    bottomPanel.add(backButton);

    mainPanel.add(titleLabel, BorderLayout.NORTH);
    mainPanel.add(selectionPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    fondo.add(mainPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
}

    private JPanel createItemSelectionPanel(String playerName) {
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JLabel nameLabel = new JLabel(playerName, SwingConstants.CENTER);
        nameLabel.setFont(new Font(font, Font.BOLD, 16));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(0, 51, 102)); // Fondo azul oscuro
        nameLabel.setForeground(new Color(204, 255, 204)); // Texto verde claro
        nameLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2)); // Borde azul oscuro
    
        List<String> availableItems = Poobkemon.getAvailableItems();
        DefaultListModel<String> itemModel = new DefaultListModel<>();
        availableItems.forEach(itemModel::addElement);
    
        JList<String> itemList = new JList<>(itemModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setVisibleRowCount(5);
        itemList.setFont(new Font(font, Font.PLAIN, 14));
        itemList.setBackground(new Color(0, 51, 102)); // Fondo azul oscuro
        itemList.setForeground(new Color(204, 255, 204)); // Texto verde claro
        itemList.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2)); // Borde azul oscuro
    
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
    
        JButton addButton = createMenuButton("ADD", new Font(font, Font.BOLD, 14), true, 100, 40);
        DefaultListModel<String> selectedItemModel = new DefaultListModel<>();
        JList<String> selectedItemList = new JList<>(selectedItemModel);
        selectedItemList.setVisibleRowCount(5);
        selectedItemList.setFont(new Font(font, Font.PLAIN, 14));
        selectedItemList.setBackground(new Color(0, 51, 102)); // Fondo azul oscuro
        selectedItemList.setForeground(new Color(204, 255, 204)); // Texto verde claro
        selectedItemList.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2)); // Borde azul oscuro
    
        addButton.addActionListener(e -> {
            String selectedItem = itemList.getSelectedValue();
            if (selectedItem != null && selectedItemModel.size() < 4) {
                selectedItemModel.addElement(selectedItem);
            } else if (selectedItemModel.size() >= 4) {
                JOptionPane.showMessageDialog(this, "Solo puedes seleccionar 4 ítems.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        JButton removeButton = createMenuButton("REMOVE", new Font(font, Font.BOLD, 14), true, 100, 40);
        removeButton.addActionListener(e -> {
            String selectedItem = selectedItemList.getSelectedValue();
            if (selectedItem != null) {
                selectedItemModel.removeElement(selectedItem);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un ítem para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(true);
        topPanel.add(nameLabel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(selectedItemList), BorderLayout.CENTER);
    
        playerPanel.add(topPanel, BorderLayout.NORTH);
        playerPanel.add(bottomPanel, BorderLayout.CENTER);
    
        playerPanel.putClientProperty("selectedItemList", selectedItemList);
    
        return playerPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PoobkemonGUI ventana = new PoobkemonGUI();
            ventana.setVisible(true);
        });
    }
    
    private void showPokemonSummaryScreen(List<String> player1Pokemon, List<String> player2Pokemon) {
        getContentPane().removeAll();
        fondo.setImagenFija(rutaImagen);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel titleLabel = new JLabel("Selecciona los movimientos", SwingConstants.CENTER);
        titleLabel.setFont(new Font(font, Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        
        // Panel principal dividido en dos secciones
        JPanel playersPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // Dos columnas, una para cada jugador
        playersPanel.setOpaque(false);
        
        // Crear las secciones para cada jugador
        JPanel player1Section = createPlayerSection("Jugador 1", player1Pokemon);
        JPanel player2Section = createPlayerSection("Jugador 2", player2Pokemon);
        
        playersPanel.add(player1Section);
        playersPanel.add(player2Section);
    
        // Panel de botones (FINALIZAR y BACK)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
    
        JButton nextButton = createMenuButton("FINALIZAR", new Font(font, Font.BOLD, 18), true, 200, 50);
        nextButton.addActionListener(e -> {
            // Validar que todos los Pokémon tengan movimientos seleccionados
            if (!areAllMovesSelected(player1Pokemon, "Jugador 1") || !areAllMovesSelected(player2Pokemon, "Jugador 2")) {
                JOptionPane.showMessageDialog(this, "Todos los Pokémon deben tener 4 movimientos seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Mostrar la pantalla de batalla
            showBattleScreen(player1Pokemon, player2Pokemon);
        });
    
        JButton backButton = createMenuButton("BACK", new Font(font, Font.BOLD, 18), true, 200, 50);
        backButton.addActionListener(e -> showPokemonSelectionScreen()); // Regresa a la pantalla anterior
    
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
    
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(playersPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        fondo.add(mainPanel);
        revalidate();
        repaint();
    }
    
    private boolean areAllMovesSelected(List<String> pokemons, String player) {
        for (String pokemon : pokemons) {
            String key = player + "_" + pokemon;
            List<String> moves = selectedMoves.get(key);
            if (moves == null || moves.size() < 4) {
                return false; // Faltan movimientos para este Pokémon
            }
        }
        return true; // Todos los Pokémon tienen movimientos seleccionados
    }
    
    private JPanel createPlayerSection(String playerName, List<String> pokemons) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título del jugador
        JLabel playerLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerLabel.setFont(new Font(font, Font.BOLD, 20));
        playerLabel.setForeground(Color.YELLOW);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Panel para los Pokémon en formato de cuadrícula (2 filas x 3 columnas)
        JPanel pokemonGrid = new JPanel(new GridLayout(2, 3, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibujar bordes de cristal verde alrededor de cada celda
                int rows = 2;
                int cols = 3;
                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = col * cellWidth;
                        int y = row * cellHeight;

                        // Gradiente para el borde
                        GradientPaint gradient = new GradientPaint(
                            x, y, new Color(34, 139, 34, 180), // Verde translúcido
                            x + cellWidth, y + cellHeight, new Color(50, 205, 50, 180) // Verde más claro
                        );
                        g2.setPaint(gradient);
                        g2.setStroke(new BasicStroke(4)); // Grosor del borde
                        g2.drawRoundRect(x + 5, y + 5, cellWidth - 10, cellHeight - 10, 15, 15); // Bordes redondeados
                    }
                }

                g2.dispose();
            }
        };
        pokemonGrid.setOpaque(false);

        for (String pokemon : pokemons) {
            // Construir la ruta relativa de la imagen
            String imagePath = "Poobkemon/mult/" + pokemon.toLowerCase() + "Front.png";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);

                JButton btn = new JButton(icon);
                btn.setPreferredSize(new Dimension(100, 100));
                btn.addActionListener(e -> showMoveSelectionDialog(pokemon, playerName));

                // Ajustar imagen al tamaño del botón
                btn.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                    80, 80, Image.SCALE_SMOOTH)));
                btn.setBorder(BorderFactory.createEmptyBorder()); // Sin borde adicional
                btn.setContentAreaFilled(false);

                pokemonGrid.add(btn);
            } else {
                System.err.println("Imagen no encontrada para: " + pokemon + " en " + imagePath);
            }
        }

        section.add(playerLabel, BorderLayout.NORTH);
        section.add(pokemonGrid, BorderLayout.CENTER);

        return section;
    }
    
    private void showMoveSelectionDialog(String pokemon, String player) {
    JDialog dialog = new JDialog(this, player + " - Seleccionar movimientos para " + pokemon, true);
    dialog.setSize(500, 500);
    dialog.setLocationRelativeTo(this);

    // Panel principal con la imagen de fondo
    JPanel mainPanel = new JPanel(new BorderLayout()) {
        private final Image backgroundImage = new ImageIcon("Poobkemon/mult/fondoelecion.jpeg").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar la imagen de fondo
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }
    };
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // --- NUEVO: Imagen del Pokémon (GIF animado) ---
    String imagePath = "Poobkemon/mult/git/" + pokemon.toLowerCase() + "front.gif";
    JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
    pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
    pokemonImage.setPreferredSize(new Dimension(120, 120));
    mainPanel.add(pokemonImage, BorderLayout.WEST); // O NORTH si prefieres arriba

    JLabel titleLabel = new JLabel("Selecciona hasta 4 movimientos para " + pokemon, SwingConstants.CENTER);
    titleLabel.setFont(new Font(font, Font.BOLD, 16));
    titleLabel.setForeground(Color.WHITE);

    // Panel para botones de tipo de ataque
    JPanel typePanel = new JPanel(new FlowLayout());
    typePanel.setOpaque(false);
    JButton physicalButton = createCrystalButton("Ataque Físico");
    JButton specialButton = createCrystalButton("Ataque Especial");
    JButton statusButton = createCrystalButton("Status");

    DefaultListModel<String> moveModel = new DefaultListModel<>();
    JList<String> moveList = new JList<>(moveModel);
    moveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    moveList.setVisibleRowCount(8);
    moveList.setFont(new Font(font, Font.PLAIN, 14));
    moveList.setBackground(new Color(0, 51, 102, 150)); // Fondo azul translúcido
    moveList.setForeground(new Color(204, 255, 204)); // Texto verde claro
    moveList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

    JScrollPane scrollPane = new JScrollPane(moveList);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);

    // Listeners para cargar ataques según el tipo
    physicalButton.addActionListener(e -> {
        moveModel.clear();
        Poobkemon.getPhysicalAttacks().forEach(moveModel::addElement);
    });

    specialButton.addActionListener(e -> {
        moveModel.clear();
        Poobkemon.getSpecialAttacks().forEach(moveModel::addElement);
    });

    statusButton.addActionListener(e -> {
        moveModel.clear();
        Poobkemon.getStatusAttacks().forEach(moveModel::addElement);
    });

    typePanel.add(physicalButton);
    typePanel.add(specialButton);
    typePanel.add(statusButton);

    mainPanel.add(titleLabel, BorderLayout.NORTH);
    mainPanel.add(typePanel, BorderLayout.NORTH);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Lista temporal para mostrar los movimientos seleccionados
    DefaultListModel<String> selectedMoveModel = new DefaultListModel<>();
    JList<String> selectedMoveList = new JList<>(selectedMoveModel) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo translúcido con gradiente
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0, 51, 102, 150), // Azul translúcido
                0, getHeight(), new Color(0, 102, 153, 150) // Azul más claro translúcido
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
            super.paintComponent(g);
        }
    };
    selectedMoveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectedMoveList.setVisibleRowCount(4);
    selectedMoveList.setFont(new Font(font, Font.PLAIN, 14));
    selectedMoveList.setBackground(new Color(0, 51, 102, 150)); // Fondo azul translúcido
    selectedMoveList.setForeground(new Color(204, 255, 102)); // Texto verde claro
    selectedMoveList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2)); // Borde azul

    // ScrollPane con estilo translúcido
    JScrollPane selectedScrollPane = new JScrollPane(selectedMoveList) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo translúcido con gradiente
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0, 51, 102, 100), // Azul translúcido
                0, getHeight(), new Color(0, 102, 153, 100) // Azul más claro translúcido
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
            super.paintComponent(g);
        }
    };
    selectedScrollPane.setOpaque(false);
    selectedScrollPane.getViewport().setOpaque(false);

    // Botón "Agregar"
    JButton addButton = createCrystalButton("Agregar");
    addButton.addActionListener(e -> {
        String selectedMove = moveList.getSelectedValue();
        if (selectedMove != null) {
            if (selectedMoveModel.size() < 4) {
                selectedMoveModel.addElement(selectedMove);
            } else {
                JOptionPane.showMessageDialog(dialog, "No puedes agregar más de 4 movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "Selecciona un movimiento para agregar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Botón "Confirmar"
    JButton confirmButton = createCrystalButton("Confirmar");
    confirmButton.addActionListener(e -> {
        if (selectedMoveModel.size() < 4) {
            JOptionPane.showMessageDialog(dialog, "Debes seleccionar exactamente 4 movimientos para continuar.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // No permite continuar si no hay 4 movimientos seleccionados
        }

        List<String> selectedMovesList = new ArrayList<>();
        for (int i = 0; i < selectedMoveModel.size(); i++) {
            selectedMovesList.add(selectedMoveModel.getElementAt(i));
        }

        // Guardar los movimientos seleccionados
        selectedMoves.put(player + "_" + pokemon, selectedMovesList);
        System.out.println(player + " seleccionó movimientos para " + pokemon + ": " + selectedMovesList);
        dialog.dispose();
    });

    // Panel para los botones y lista de movimientos seleccionados
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setOpaque(false);
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    buttonPanel.add(addButton);
    buttonPanel.add(confirmButton);

    bottomPanel.add(selectedScrollPane, BorderLayout.CENTER);
    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    dialog.add(mainPanel);
    dialog.setVisible(true);
}
    
    private void showBattleScreen(List<String> player1Pokemon, List<String> player2Pokemon) {
    this.player1Pokemon = player1Pokemon;
    this.player2Pokemon = player2Pokemon;

    getContentPane().removeAll();
    fondo.setImagenFija(rutaImagenBattle);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setOpaque(false);

    // Panel central: Pokémon de ambos jugadores
    JPanel battlePanel = new JPanel(new GridLayout(1, 2, 20, 0));
    battlePanel.setOpaque(false);

    this.player1Panel = createBattlePokemonPanel(player1Pokemon.get(0), true);
    this.player2Panel = createBattlePokemonPanel(player2Pokemon.get(0), false);

    battlePanel.add(player1Panel);
    battlePanel.add(player2Panel);

    // Panel inferior: Botones de acción y Pokébolas
    JPanel actionPanel = createBattleActionPanel();

    // Etiqueta para mostrar el tiempo restante
    turnTimerLabel = new JLabel("Tiempo restante: 20 segundos", SwingConstants.CENTER);
    turnTimerLabel.setFont(new Font(font, Font.BOLD, 16));
    turnTimerLabel.setForeground(Color.WHITE);

    mainPanel.add(battlePanel, BorderLayout.CENTER);
    mainPanel.add(actionPanel, BorderLayout.SOUTH);
    mainPanel.add(turnTimerLabel, BorderLayout.NORTH);

    fondo.add(mainPanel, BorderLayout.CENTER);
    revalidate();
    repaint();

    startTurnTimer(); // Iniciar el temporizador del turno
}
    
    private JPanel createBattlePokemonPanel(String pokemonName, boolean isPlayer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
    
        // Barra de vida en la parte superior
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        panel.add(healthBar, BorderLayout.NORTH);
    
        // Imagen del Pokémon (GIF animado)
        String imagePath = "Poobkemon/mult/git/" + pokemonName.toLowerCase() + (isPlayer ? "back.gif" : "front.gif");
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImage.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(pokemonImage, BorderLayout.CENTER);
    
        panel.putClientProperty("healthBar", healthBar);
        return panel;
    }
    
    private JPanel createBattleActionPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
    panel.setOpaque(false);

    // Panel de movimientos y Pokébolas para el jugador 1
    JPanel player1Actions = new JPanel(new BorderLayout());
    player1Actions.setOpaque(false);
    player1Actions.add(createMovesPanel(player1Pokemon, player1Panel, true), BorderLayout.CENTER);
    player1Actions.add(createPokeballPanel(player1Pokemon, player1Panel, true), BorderLayout.SOUTH);

    // Panel de movimientos y Pokébolas para el jugador 2
    JPanel player2Actions = new JPanel(new BorderLayout());
    player2Actions.setOpaque(false);
    player2Actions.add(createMovesPanel(player2Pokemon, player2Panel, false), BorderLayout.CENTER);
    player2Actions.add(createPokeballPanel(player2Pokemon, player2Panel, false), BorderLayout.SOUTH);

    panel.add(player1Actions);
    panel.add(player2Actions);

    return panel;
}
    
    private JPanel createMovesPanel(List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
        JPanel movesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movesPanel.setOpaque(false);
    
        // Solo limpiar y llenar las listas de botones en batalla
        List<JButton> moveButtons = isPlayer1 ? player1BattleMoveButtons : player2BattleMoveButtons;
        moveButtons.clear();
    
        String currentPlayer = isPlayer1 ? "Jugador 1" : "Jugador 2";
        String currentPokemon = pokemonList.get(0); // Pokémon activo
        List<String> moves = selectedMoves.get(currentPlayer + "_" + currentPokemon);
    
        for (int i = 0; i < 4; i++) {
            String moveName = (moves != null && i < moves.size()) ? moves.get(i) : "N/A";
            JButton moveButton = new JButton(moveName);
            moveButton.setFont(new Font(font, Font.BOLD, 14));
            moveButton.setEnabled(isPlayer1Turn == isPlayer1 && !"N/A".equals(moveName));
            moveButton.addActionListener(e -> handleMoveAction(moveName, pokemonList, playerPanel, isPlayer1));
            movesPanel.add(moveButton);
            moveButtons.add(moveButton);
        }
    
        return movesPanel;
    }
    
    private void handleMoveAction(String moveName, List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
    if ("N/A".equals(moveName)) {
        JOptionPane.showMessageDialog(this, "Movimiento no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Detén el temporizador porque el jugador realizó una acción
    if (turnTimer != null) {
        turnTimer.stop();
    }

    // Lógica para manejar el ataque
    List<String> opponentPokemonList = isPlayer1 ? player2Pokemon : player1Pokemon;
    JPanel opponentPanel = isPlayer1 ? player2Panel : player1Panel;

    int damage = (int) (Math.random() * 20) + 10; // Daño aleatorio
    JProgressBar opponentHealthBar = (JProgressBar) opponentPanel.getClientProperty("healthBar");
    int newHealth = Math.max(0, opponentHealthBar.getValue() - damage);
    opponentHealthBar.setValue(newHealth);

    if (newHealth == 0) {
        JOptionPane.showMessageDialog(this, opponentPokemonList.get(0) + " ha sido derrotado.", "Batalla", JOptionPane.INFORMATION_MESSAGE);
        opponentPokemonList.remove(0);
        if (opponentPokemonList.isEmpty()) {
            JOptionPane.showMessageDialog(this, (isPlayer1 ? "Jugador 1" : "Jugador 2") + " ha ganado la batalla.", "Fin de la Batalla", JOptionPane.INFORMATION_MESSAGE);
            returnToGame();
            return;
        }
        updateBattlePokemonPanel(opponentPanel, opponentPokemonList.get(0), !isPlayer1);
    }

    switchTurn(); // Cambiar el turno después de realizar un movimiento
}
    
    private void updateBattlePokemonPanel(JPanel panel, String pokemonName, boolean isPlayer) {
    // Obtener la barra de vida existente (si ya está configurada)
    JProgressBar existingHealthBar = (JProgressBar) panel.getClientProperty("healthBar");
    int currentHealth = 100; // Valor por defecto si no hay barra existente

    if (existingHealthBar != null) {
        currentHealth = existingHealthBar.getValue(); // Mantener la vida actual
    }

    panel.removeAll(); // Limpiar el panel

    // Imagen del Pokémon (GIF animado)
    String imagePath = "Poobkemon/mult/git/" + pokemonName.toLowerCase() + (isPlayer ? "back.gif" : "front.gif");
    JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
    pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
    pokemonImage.setVerticalAlignment(SwingConstants.CENTER);
    panel.add(pokemonImage, BorderLayout.CENTER);

    // Barra de vida
    JProgressBar healthBar = new JProgressBar(0, 100);
    healthBar.setValue(currentHealth); // Usar la vida actual del Pokémon
    healthBar.setStringPainted(true);
    healthBar.setForeground(Color.GREEN);
    panel.add(healthBar, BorderLayout.SOUTH);

    // Guardar la barra de vida en las propiedades del panel
    panel.putClientProperty("healthBar", healthBar);

    panel.revalidate();
    panel.repaint();
}
    
    private void handlePokemonSwitch(List<String> playerPokemon) {
        JDialog dialog = new JDialog(this, "Cambiar Pokémon", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
    
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        for (String pokemon : playerPokemon) {
            JButton button = new JButton(pokemon);
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Has cambiado a " + pokemon, "Información", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            });
            panel.add(button);
        }
    
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void handleRunAction() {
        JOptionPane.showMessageDialog(this, "Has huido de la batalla.", "Información", JOptionPane.INFORMATION_MESSAGE);
        returnToGame();
    }
    
    private void printFinalSelection(List<String> player1Pokemon, List<String> player2Pokemon) {
        System.out.println("\n=== Selección Final ===");
        
        printPlayerSelection("Player 1", player1Pokemon);
        printPlayerSelection("Player 2", player2Pokemon);
    }
    
    private void printPlayerSelection(String player, List<String> pokemons) {
        System.out.println("\n" + player + ":");
    
        // Obtener ítems del jugador
        List<String> items = playerItems.getOrDefault(player, new ArrayList<>());
        if (items.isEmpty()) {
            System.out.println("Ítems: No seleccionados");
        } else {
            System.out.println("Ítems: " + String.join(", ", items));
        }
    
        // Imprimir cada Pokémon con sus movimientos
        for (String pokemon : pokemons) {
            String key = player + "_" + pokemon;
            List<String> moves = selectedMoves.getOrDefault(key, new ArrayList<>());
            if (moves.isEmpty()) {
                System.out.println("- " + pokemon + ": No seleccionados");
            } else {
                System.out.println("- " + pokemon + ": " + String.join(", ", moves));
            }
        }
    }
    
    private JPanel createPokeballPanel(List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
        JPanel pokeballPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pokeballPanel.setOpaque(false);
    
        List<JButton> pokeballButtons = isPlayer1 ? player1BattlePokeballButtons : player2BattlePokeballButtons;
        pokeballButtons.clear();
    
        for (int i = 0; i < pokemonList.size(); i++) {
            String pokemonName = pokemonList.get(i);
            JButton pokeballButton = new JButton(new ImageIcon("Poobkemon/mult/pokeball.jpeg"));
            pokeballButton.setPreferredSize(new Dimension(50, 50));
            pokeballButton.setEnabled(isPlayer1Turn == isPlayer1);
            pokeballButton.addActionListener(e -> updateBattlePokemonPanel(playerPanel, pokemonName, isPlayer1));
            pokeballPanel.add(pokeballButton);
            pokeballButtons.add(pokeballButton);
        }
    
        return pokeballPanel;
    }
    
    private JButton createCrystalButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo translúcido con gradiente
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(34, 139, 230, 180), // Azul translúcido
                0, getHeight(), new Color(50, 205, 50, 180) // Verde translúcido
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // Borde brillante
            g2.setColor(new Color(255, 255, 255, 150));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No pintar borde por defecto
        }
    };

    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(false);

    return button;
}
    
    private void startTurnTimer() {
    if (turnTimer != null) {
        turnTimer.stop(); // Detén el temporizador anterior si existe
    }

    turnTimeRemaining = 20; // Reiniciar el tiempo a 20 segundos
    turnTimerLabel.setText("Tiempo restante: " + turnTimeRemaining + " segundos");

    // Crear un nuevo temporizador
    turnTimer = new Timer(1000, e -> {
        if (turnTimeRemaining > 0) {
            turnTimeRemaining--;
            turnTimerLabel.setText("Tiempo restante: " + turnTimeRemaining + " segundos");
        } else {
            turnTimer.stop(); // Detén el temporizador
            JOptionPane.showMessageDialog(this, "Tiempo agotado. Turno del siguiente jugador.", "Turno", JOptionPane.WARNING_MESSAGE);
            switchTurn(); // Cambia el turno automáticamente
        }
    });

    turnTimer.start(); // Inicia el temporizador
}
    
    private void switchTurn() {
    isPlayer1Turn = !isPlayer1Turn; // Cambiar el turno
    updateBattleButtons(); // Actualizar los botones según el turno
    startTurnTimer(); // Reiniciar el temporizador para el nuevo turno
}
    
    private void updateBattleButtons() {
    for (JButton btn : player1BattleMoveButtons) btn.setEnabled(isPlayer1Turn);
    for (JButton btn : player2BattleMoveButtons) btn.setEnabled(!isPlayer1Turn);
    for (JButton btn : player1BattlePokeballButtons) btn.setEnabled(isPlayer1Turn);
    for (JButton btn : player2BattlePokeballButtons) btn.setEnabled(!isPlayer1Turn);
}
    
}