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
    
    // Panel principal con BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.BLACK);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    // --- Panel izquierdo (botones) ---
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setLayout(new GridLayout(4, 1, 15, 20)); // Más espacio entre botones
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40)); // Más margen derecho

    Font buttonFont = new Font("Arial", Font.BOLD, 18); // Tamaño de fuente aumentado
        
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
        
    // --- Panel derecho (logo Pokébola) ---
    JPanel logoPanel = new JPanel(new BorderLayout());
    logoPanel.setBackground(Color.BLACK);
    
    try {
        String imagePath = System.getProperty("user.dir") + "/Poobkemon/mult/pokeball.png";
        ImageIcon pokeballIcon = new ImageIcon(imagePath);
        
        // Mejor escalado de imagen con renderizado suave
        Image image = pokeballIcon.getImage();
        Image newimg = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        
        JLabel logoLabel = new JLabel(new ImageIcon(newimg)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                super.paintComponent(g2);
            }
        };
        
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBackground(Color.BLACK);
        logoPanel.add(logoLabel, BorderLayout.CENTER);
    } catch (Exception e) {
        JLabel errorLabel = new JLabel("POKEBALL IMAGE", SwingConstants.CENTER);
        errorLabel.setForeground(new Color(200, 50, 50));
        errorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        errorLabel.setBackground(Color.BLACK);
        errorLabel.setOpaque(true);
        logoPanel.add(errorLabel);
    }
        
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
        // Limpia el contenido actual
        getContentPane().removeAll();

        // Panel principal con fondo negro
        JPanel typeGamePanel = new JPanel();
        typeGamePanel.setBackground(Color.BLACK);
        typeGamePanel.setLayout(new BoxLayout(typeGamePanel, BoxLayout.Y_AXIS));
        typeGamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título "Type Game"
        JLabel titleLabel = new JLabel("Type Game");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        typeGamePanel.add(titleLabel);

        typeGamePanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botones rojos
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Color buttonColor = new Color(200, 0, 0);

        JButton humanVsHumanBtn = new JButton("Human vs Human");
        humanVsHumanBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanBtn.setFont(buttonFont);
        humanVsHumanBtn.setBackground(buttonColor);
        humanVsHumanBtn.setForeground(Color.WHITE);
        humanVsHumanBtn.setFocusPainted(false);
        humanVsHumanBtn.setMaximumSize(new Dimension(300, 50));
        humanVsHumanBtn.addActionListener(e -> System.out.println("Human vs Human selected"));
        typeGamePanel.add(humanVsHumanBtn);

        typeGamePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton humanVsMachineBtn = new JButton("Human vs Machine");
        humanVsMachineBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsMachineBtn.setFont(buttonFont);
        humanVsMachineBtn.setBackground(buttonColor);
        humanVsMachineBtn.setForeground(Color.WHITE);
        humanVsMachineBtn.setFocusPainted(false);
        humanVsMachineBtn.setMaximumSize(new Dimension(300, 50));
        humanVsMachineBtn.addActionListener(e -> System.out.println("Human vs Machine selected"));
        typeGamePanel.add(humanVsMachineBtn);

        typeGamePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton machineVsMachineBtn = new JButton("Machine vs Machine");
        machineVsMachineBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        machineVsMachineBtn.setFont(buttonFont);
        machineVsMachineBtn.setBackground(buttonColor);
        machineVsMachineBtn.setForeground(Color.WHITE);
        machineVsMachineBtn.setFocusPainted(false);
        machineVsMachineBtn.setMaximumSize(new Dimension(300, 50));
        machineVsMachineBtn.addActionListener(e -> System.out.println("Machine vs Machine selected"));
        typeGamePanel.add(machineVsMachineBtn);

        typeGamePanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botón Back para volver a la pantalla anterior
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setFont(buttonFont);
        backBtn.setBackground(new Color(70, 130, 180));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setMaximumSize(new Dimension(150, 40));
        backBtn.addActionListener(e -> returnToGame());
        typeGamePanel.add(backBtn);

        getContentPane().add(typeGamePanel);
        revalidate();
        repaint();
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