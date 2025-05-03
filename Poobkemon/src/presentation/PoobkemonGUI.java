package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLabelUI;

public class PoobkemonGUI extends JFrame {
    private FondoAnimado fondo;
    private String font = "Times New Roman";
    private ReproductorMusica reproductor;

    public PoobkemonGUI() {
        super("Poobkemon Garcia-Romero");
        prepareElementsDimension();
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

    private void prepareBackground(){
        fondo = new FondoAnimado("Poobkemon/mult/pokemonIntro.gif");
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
        reproductor = new ReproductorMusica("Poobkemon/mult/musicaIntro.wav");
    }


    private void prepareElementsDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = screenSize.width/2;
        int windowHeight = screenSize.height/2;
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
    class ReproductorMusica {
        private Clip clip;
        private boolean estaReproduciendo = true;

        public ReproductorMusica(String rutaArchivo) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
                clip = AudioSystem.getClip();

                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir sin fin
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error al reproducir música: " + e.getMessage());
            }
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


    private void start() {
        // Cambia a imagen fija para la segunda pantalla
        fondo.setImagenFija("Poobkemon/mult/fondo1.jpeg"); // Cambia por tu ruta de imagen
        
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
            JButton continueBtn = createMenuButton("CONTINUE", buttonFont, true);
            continueBtn.addActionListener(e -> continueGame());
            buttonPanel.add(continueBtn);
        }

        JButton newGameBtn = createMenuButton("NEW GAME", buttonFont, true);
        JButton oldGameBtn = createMenuButton("OLD GAME", buttonFont, true);
        JButton scoreBtn = createMenuButton("SCORE", buttonFont, true);
            
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
        
        JButton backBtn = createMenuButton("BACK", buttonFont, true);
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

private JButton createMenuButton(String text, Font font, boolean enabled) {
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
    button.setPreferredSize(new Dimension(220, 55)); // Tamaño ligeramente más grande
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
        fondo.setImagenFija("Poobkemon/mult/fondo1.jpeg");
        
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
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
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
        JButton normalModeBtn = createMenuButton("NORMAL MODE", buttonFont, true);
        normalModeBtn.addActionListener(e -> showGameTypeSelection("NORMAL"));

        // Botón para modo Survival
        JButton survivalModeBtn = createMenuButton("SURVIVAL MODE", buttonFont, true);
        survivalModeBtn.addActionListener(e -> showGameTypeSelection("SURVIVAL"));

        buttonPanel.add(normalModeBtn);
        buttonPanel.add(survivalModeBtn);

        // Botón Back (alineado a la derecha)
        JButton backBtn = createMenuButton("BACK", buttonFont, true);
        backBtn.addActionListener(e -> start()); // Volver al menú principal
        
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
        getContentPane().removeAll();

        JPanel typeGamePanel = new JPanel(new BorderLayout());
        typeGamePanel.setBackground(Color.BLACK);
        typeGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel titleLabel = new JLabel("GAME TYPE (" + gameMode + " MODE)", SwingConstants.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("SERIF", Font.BOLD, 28));
        typeGamePanel.add(titleLabel, BorderLayout.NORTH);
    
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
    
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setBackground(Color.BLACK);
    
        // Paneles de imágenes responsivas (25% del ancho cada uno)
        JPanel leftImagesPanel = createImagePanel("human.png", "human.png", "porygon.png");
        leftImagesPanel.setPreferredSize(new Dimension(getWidth()/4, getHeight()));
    
        JPanel rightImagesPanel = createImagePanel("human.png", "porygon.png", "porygon.png");
        rightImagesPanel.setPreferredSize(new Dimension(getWidth()/4, getHeight()));
    
        contentPanel.add(leftImagesPanel, BorderLayout.WEST);
        contentPanel.add(rightImagesPanel, BorderLayout.EAST);
    
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    
        JButton humanVsHumanBtn = createMenuButton("Human vs Human", buttonFont, true);
        humanVsHumanBtn.addActionListener(e -> {
            System.out.println(gameMode + " mode - Human vs Human selected");
            // Aquí iniciarías el juego con los parámetros seleccionados
        });
    
        JButton humanVsMachineBtn = createMenuButton("Human vs Machine", buttonFont, true);
        humanVsMachineBtn.addActionListener(e -> {
            System.out.println(gameMode + " mode - Human vs Machine selected");
            // Aquí iniciarías el juego con los parámetros seleccionados
        });
    
        JButton machineVsMachineBtn = createMenuButton("Machine vs Machine", buttonFont, true);
        machineVsMachineBtn.addActionListener(e -> {
            System.out.println(gameMode + " mode - Machine vs Machine selected");
            // Aquí iniciarías el juego con los parámetros seleccionados
        });
    
        buttonPanel.add(humanVsHumanBtn);
        buttonPanel.add(humanVsMachineBtn);
        buttonPanel.add(machineVsMachineBtn);
    
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(Color.BLACK);
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(buttonContainer, BorderLayout.CENTER);
    
        typeGamePanel.add(contentPanel, BorderLayout.CENTER);
    
        JButton backBtn = createMenuButton("BACK", buttonFont, true);
        backBtn.addActionListener(e -> startNewGame()); // Volver a selección de modo
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backBtn);
        
        typeGamePanel.add(bottomPanel, BorderLayout.SOUTH);
    
        getContentPane().add(typeGamePanel);
        revalidate();
        repaint();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PoobkemonGUI ventana = new PoobkemonGUI();
            ventana.setVisible(true);
        });
    }
}