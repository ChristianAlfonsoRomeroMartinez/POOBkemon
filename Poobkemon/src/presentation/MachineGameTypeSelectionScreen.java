package presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MachineGameTypeSelectionScreen extends JPanel {

    private JFrame parentFrame;

    public MachineGameTypeSelectionScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setOpaque(false);

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Selecciona el tipo de juego para la máquina", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

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

        add(buttonPanel, BorderLayout.CENTER);

        // Botón para regresar
        JButton backButton = createMenuButton("Volver", e -> returnToPreviousScreen());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(34, 139, 230));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(actionListener);
        return button;
    }

    private void handleGameTypeSelection(String gameType) {
        // Imprimir en consola la elección del usuario
        System.out.println("Modo de juego seleccionado para la máquina: " + gameType);

        // Aquí puedes implementar la lógica para iniciar el juego con la estrategia seleccionada
        JOptionPane.showMessageDialog(this, "Has seleccionado la estrategia: " + gameType, "Estrategia Seleccionada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void returnToPreviousScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new PoobkemonGUI()); // Regresa al menú principal
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}