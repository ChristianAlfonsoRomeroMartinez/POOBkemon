package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PoobkemonGUI extends JFrame {

    public PoobkemonGUI() {
        super("Poobkemon Garcia-Romero");
        prepareElementsDimension();
        prepareButtons();
        setupWindowListeners();
    }

    private void prepareElementsDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = screenSize.width / 2;
        int windowHeight = screenSize.height / 2;
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
    }

    private void prepareButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 0, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel title = new JLabel("Poobkemon 2025-1", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title);

        // Botón Start
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(e -> start());
        panel.add(startButton);

        // Botón Exit
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(e -> exitApplication());
        panel.add(exitButton);

        getContentPane().add(panel, BorderLayout.CENTER);
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

    private void start() {
    // Limpia el contenido actual
    getContentPane().removeAll();
    getContentPane().setBackground(Color.BLACK);
    getContentPane().setLayout(new BorderLayout());
    
    // Panel principal con BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.BLACK);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    // --- Panel izquierdo (botones) ---
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setLayout(new GridLayout(4, 1, 15, 20));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));

    Font buttonFont = new Font("Arial", Font.BOLD, 18);
        
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
        
    // --- Panel derecho (logo Pokébola) - Ahora responsive ---
    JPanel logoPanel = new JPanel(new BorderLayout()) {
        private Image originalImage;
        private Image scaledImage;
        
        {
            try {
                String imagePath = System.getProperty("user.dir") + "/Poobkemon/mult/pokeball.png";
                originalImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                originalImage = null;
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            if (originalImage != null) {
                // Calcular tamaño manteniendo relación de aspecto
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                
                // Tamaño máximo para la imagen (80% del panel para dejar márgenes)
                int maxWidth = (int)(panelWidth * 1.5);
                int maxHeight = (int)(panelHeight * 1.5);
                
                // Calcular dimensiones manteniendo aspect ratio
                double aspectRatio = (double)originalImage.getWidth(null) / originalImage.getHeight(null);
                int newWidth = maxWidth;
                int newHeight = (int)(newWidth / aspectRatio);
                
                if (newHeight > maxHeight) {
                    newHeight = maxHeight;
                    newWidth = (int)(newHeight * aspectRatio);
                }
                
                // Escalar solo si es necesario
                if (scaledImage == null || 
                    scaledImage.getWidth(null) != newWidth || 
                    scaledImage.getHeight(null) != newHeight) {
                    scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                }
                
                // Centrar la imagen
                int x = (panelWidth - newWidth) / 2;
                int y = (panelHeight - newHeight) / 2;
                g2.drawImage(scaledImage, x, y, this);
            } else {
                // Mostrar mensaje de error si no se carga la imagen
                g2.setColor(new Color(200, 50, 50));
                g2.setFont(new Font("Arial", Font.BOLD, 24));
                String text = "POKEBALL IMAGE";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(text, x, y);
            }
        }
    };
    logoPanel.setBackground(Color.BLACK);
    
    // Listener para redimensionamiento
    this.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            logoPanel.repaint(); // Redibuja la imagen cuando cambia el tamaño
        }
    });
        
    // --- Botón BACK abajo ---
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBackground(Color.BLACK);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JButton backBtn = createMenuButton("BACK", buttonFont, true);
    backBtn.addActionListener(e -> returnToGame());
    
    // Centrar el botón BACK
    JPanel backBtnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    backBtnContainer.setBackground(Color.BLACK);
    backBtnContainer.add(backBtn);
    bottomPanel.add(backBtnContainer, BorderLayout.EAST);
        
    // --- Ensamblar componentes ---
    mainPanel.add(buttonPanel, BorderLayout.WEST);
    mainPanel.add(logoPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
    getContentPane().add(mainPanel);
    revalidate();
    repaint();
}

private JButton createMenuButton(String text, Font font, boolean enabled) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fondo con gradiente y esquinas redondeadas
            if (isEnabled()) {
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(180, 40, 40), 
                    0, getHeight(), new Color(120, 20, 20)
                );
                g2.setPaint(gp);
            } else {
                g2.setColor(new Color(60, 60, 60));
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            
            // Borde con efecto de resaltado
            g2.setColor(isEnabled() ? new Color(255, 255, 255, 120) : new Color(100, 100, 100));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            
            super.paintComponent(g2);
            g2.dispose();
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            // No pintar borde por defecto
        }
    };
    
    button.setFont(font);
    button.setEnabled(enabled);
    button.setPreferredSize(new Dimension(200, 50)); // Tamaño aumentado
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
    
    // Efecto hover mejorado
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (button.isEnabled()) {
                button.setForeground(new Color(255, 255, 200)); // Texto amarillo claro
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            if (button.isEnabled()) {
                button.setForeground(Color.WHITE);
            }
        }
    });
    
    return button;
}

    // Métodos de ejemplo para las acciones
    private void continueGame() {
        System.out.println("Continuar partida");
    }
    private void startNewGame() {
        getContentPane().removeAll();
    
        JPanel typeGamePanel = new JPanel(new BorderLayout());
        typeGamePanel.setBackground(Color.BLACK);
        typeGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Título
        JLabel titleLabel = new JLabel("Type Game", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        typeGamePanel.add(titleLabel, BorderLayout.NORTH);
    
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
    
        // Panel central: Imágenes + Botones
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setBackground(Color.BLACK);
    
        // Panel Izquierdo: Imágenes
        JPanel leftImagesPanel = createImagePanel("human.png", "human.png", "walli.png");
        contentPanel.add(leftImagesPanel, BorderLayout.WEST);
    
        // Panel Derecho: Imágenes
        JPanel rightImagesPanel = createImagePanel("human.png", "walli.png", "walli.png");
        contentPanel.add(rightImagesPanel, BorderLayout.EAST);
    
        // Botones Centrados
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    
        JButton humanVsHumanBtn = createMenuButton("Human vs Human", buttonFont, true);
        humanVsHumanBtn.addActionListener(e -> System.out.println("Human vs Human selected"));
    
        JButton humanVsMachineBtn = createMenuButton("Human vs Machine", buttonFont, true);
        humanVsMachineBtn.addActionListener(e -> System.out.println("Human vs Machine selected"));
    
        JButton machineVsMachineBtn = createMenuButton("Machine vs Machine", buttonFont, true);
        machineVsMachineBtn.addActionListener(e -> System.out.println("Machine vs Machine selected"));
    
        buttonPanel.add(humanVsHumanBtn);
        buttonPanel.add(humanVsMachineBtn);
        buttonPanel.add(machineVsMachineBtn);
    
        // Contenedor para centrar los botones verticalmente
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setBackground(Color.BLACK);
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(buttonContainer, BorderLayout.CENTER);
    
        typeGamePanel.add(contentPanel, BorderLayout.CENTER);
    
        // Botón BACK
        JButton backBtn = createMenuButton("BACK", buttonFont, true);
        backBtn.addActionListener(e -> start());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backBtn);
        
        typeGamePanel.add(bottomPanel, BorderLayout.SOUTH);
    
        getContentPane().add(typeGamePanel);
        revalidate();
        repaint();
    }
    
    // Método auxiliar para crear paneles de imágenes centradas
    private JPanel createImagePanel(String... imageNames) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
    
        panel.add(Box.createVerticalGlue()); // Espacio superior
    
        for (String imageName : imageNames) {
            try {
                String path = System.getProperty("user.dir") + "/Poobkemon/mult/" + imageName;
                ImageIcon icon = new ImageIcon(path);
                Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaled));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(label);
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
            } catch (Exception e) {
                JLabel errorLabel = new JLabel("Image not found");
                errorLabel.setForeground(Color.WHITE);
                panel.add(errorLabel);
            }
        }
    
        panel.add(Box.createVerticalGlue()); // Espacio inferior
        return panel;
    }

    private void returnToGame() {
        getContentPane().removeAll(); // Limpia el contenido actual
        prepareButtons(); // Vuelve a preparar los botones iniciales
        revalidate(); // Actualiza el diseño
        repaint(); // Redibuja la ventana
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