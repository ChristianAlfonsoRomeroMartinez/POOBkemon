package presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MachineGameTypeSelectionScreen extends JPanel {

    private JFrame parentFrame;

    public MachineGameTypeSelectionScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Aplicar imagen de fondo
        setOpaque(false);
        JLabel backgroundLabel = new JLabel(new ImageIcon("mult/machine01.jpeg"));
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Selecciona el tipo de juego para la máquina", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // Botón para "Defensive Trainer"
        JButton defensiveButton = createMenuButton("Defensive Trainer", e -> handleGameTypeSelection("defensiveTrainer"));
        buttonPanel.add(defensiveButton);

        // Botón para "Attacking Trainer"
        JButton attackingButton = createMenuButton("Attacking Trainer", e -> handleGameTypeSelection("attackingTrainer"));
        buttonPanel.add(attackingButton);

        // Botón para "Changing Trainer"
        JButton changingButton = createMenuButton("Changing Trainer", e -> handleGameTypeSelection("changingTrainer"));
        buttonPanel.add(changingButton);

        // Botón para "Expert Trainer"
        JButton expertButton = createMenuButton("Expert Trainer", e -> handleGameTypeSelection("expertTrainer"));
        buttonPanel.add(expertButton);

        backgroundLabel.add(buttonPanel, BorderLayout.CENTER);

        // Botón para regresar
        JButton backButton = createMenuButton("Volver", e -> returnToPreviousScreen());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 128, 0)); // Verde oscuro
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 0), 2), // Borde verde brillante
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(actionListener);
        return button;
    }

    private void handleGameTypeSelection(String gameType) {
        // Imprimir en consola la elección del usuario
        System.out.println("Modo de juego seleccionado para la máquina: " + gameType);

        // Adaptar el tipo de juego al formato esperado por TrainerFactory
        // En TrainerFactory se espera "defensive" o "attacking"
        String machineType = gameType.equals("defensiveTrainer") ? "defensive" : "attacking";

        // Navegar a la pantalla de selección de Pokémon
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new SinglePlayerPokemonSelectionScreen(parentFrame, machineType));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void returnToPreviousScreen() {
        // Regresa al menú principal configurando un nuevo panel
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new PoobkemonMainMenu(parentFrame)); // Cambia a un JPanel válido
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("Poobkemon/mult/machine01.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}