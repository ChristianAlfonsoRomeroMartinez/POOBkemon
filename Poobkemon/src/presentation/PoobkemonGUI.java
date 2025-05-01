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
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

         // --- Panel izquierdo (botones) ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        // Aquí iría la lógica de inicio de tu aplicación

            // Botones (solo NEW GAME habilitado inicialmente)
        if (!false) {
           JButton continueBtn = createMenuButton("CONTINUE", buttonFont, true);
            continueBtn.addActionListener(e -> continueGame());
            buttonPanel.add(continueBtn);
        }

        JButton newGameBtn = createMenuButton("NEW GAME", buttonFont, true);

        //NO OLVIDAR HACER LAS VERIFICACIONES DE QUE SI ES LA PRIMER PARTIDA NO HAY OLDAGAME
        JButton oldGameBtn = createMenuButton("OLD GAME", buttonFont, true); // Desactivado
        JButton scoreBtn = createMenuButton("SCORE", buttonFont, true); // Desactivado
        
        newGameBtn.addActionListener(e -> startNewGame());
        oldGameBtn.addActionListener(e -> {}); // Sin acción
        scoreBtn.addActionListener(e -> {}); // Sin acción
        
        buttonPanel.add(newGameBtn);
        buttonPanel.add(oldGameBtn);
        buttonPanel.add(scoreBtn);
        
        // --- Panel derecho (logo Pokébola) ---
        JPanel logoPanel = new JPanel(new BorderLayout());
        try {
            String imagePath = System.getProperty("user.dir") + "/Poobkemon/mult/pokeball.png";
            ImageIcon pokeballIcon = new ImageIcon(imagePath); // Load image from absolute path
            JLabel logoLabel = new JLabel(new ImageIcon(pokeballIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH)));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            logoPanel.add(new JLabel("mult/pokeball.png", SwingConstants.CENTER));
        }
        
        // --- Botón BACK abajo ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = createMenuButton("BACK", buttonFont, true);
        backBtn.addActionListener(e -> returnToGame());
        bottomPanel.add(backBtn);
        
        // --- Ensamblar componentes ---
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(logoPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        getContentPane().add(mainPanel);
        revalidate();
        repaint();
    }

    private JButton createMenuButton(String text, Font font, boolean enabled) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setEnabled(enabled);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBackground(enabled ? new Color(70, 130, 180) : new Color(150, 150, 150));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    // Métodos de ejemplo para las acciones
    private void continueGame() {
        System.out.println("Continuar partida");
    }

    private void startNewGame() {
        System.out.println("Nueva partida");
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