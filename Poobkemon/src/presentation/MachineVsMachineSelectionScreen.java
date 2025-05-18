package presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class MachineVsMachineSelectionScreen extends JPanel {

    private JFrame parentFrame;
    private String machine1Type = "Defensive"; // Valor por defecto
    private String machine2Type = "Attacking"; // Valor por defecto
    private Map<String, String> displayNameToMachineType = new HashMap<>();
    
    public MachineVsMachineSelectionScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        
        // Mapeo de nombres mostrados a tipos de máquina internos
        displayNameToMachineType.put("Defensive Trainer", "Defensive");
        displayNameToMachineType.put("Attacking Trainer", "Attacking");
        displayNameToMachineType.put("Strategic Trainer", "Strategic");
        displayNameToMachineType.put("Expert Trainer", "Expert");

        // Aplicar imagen de fondo
        setOpaque(false);

        // Panel principal con borde
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Batalla Máquina vs Máquina", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de selección para ambas máquinas
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        selectionPanel.setOpaque(false);
        
        // Panel para Máquina 1
        JPanel machine1Panel = createMachineSelectionPanel("Máquina 1", true);
        selectionPanel.add(machine1Panel);
        
        // Panel para Máquina 2
        JPanel machine2Panel = createMachineSelectionPanel("Máquina 2", false);
        selectionPanel.add(machine2Panel);
        
        mainPanel.add(selectionPanel, BorderLayout.CENTER);

        // Botones de navegación
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        navigationPanel.setOpaque(false);
        
        JButton backButton = createMenuButton("Volver", e -> returnToPreviousScreen());
        JButton startBattleButton = createMenuButton("¡Iniciar Batalla!", e -> startMachineVsMachineBattle());
        
        navigationPanel.add(backButton);
        navigationPanel.add(startBattleButton);
        
        mainPanel.add(navigationPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMachineSelectionPanel(String title, boolean isFirstMachine) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Título del panel
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Opciones de máquina
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        optionsPanel.setOpaque(false);
        
        String[] machineTypes = {"Defensive Trainer", "Attacking Trainer", "Strategic Trainer", "Expert Trainer"};
        ButtonGroup group = new ButtonGroup();
        
        for (String type : machineTypes) {
            JRadioButton radioButton = new JRadioButton(type);
            radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
            radioButton.setForeground(Color.WHITE);
            radioButton.setOpaque(false);
            radioButton.setFocusPainted(false);
            
            // Seleccionar por defecto
            if ((isFirstMachine && type.equals("Defensive Trainer")) || 
                (!isFirstMachine && type.equals("Attacking Trainer"))) {
                radioButton.setSelected(true);
            }
            
            // Agregar acción
            radioButton.addActionListener(e -> {
                if (isFirstMachine) {
                    machine1Type = displayNameToMachineType.get(type);
                } else {
                    machine2Type = displayNameToMachineType.get(type);
                }
                System.out.println("Seleccionado para " + title + ": " + type + " (" + 
                                   (isFirstMachine ? machine1Type : machine2Type) + ")");
            });
            
            group.add(radioButton);
            optionsPanel.add(radioButton);
        }
        
        panel.add(optionsPanel, BorderLayout.CENTER);
     
        return panel;
    }

    private JButton createMenuButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 128, 0));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 0), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(actionListener);
        return button;
    }

    private void returnToPreviousScreen() {
        PoobkemonGUI gui = (PoobkemonGUI) parentFrame;
        gui.getContentPane().removeAll();
        gui.start(); // Vuelve a la pantalla principal
        gui.revalidate();
        gui.repaint();
    }
    
    private void startMachineVsMachineBattle() {
        System.out.println("Iniciando batalla Machine vs Machine");
        System.out.println("Máquina 1: " + machine1Type);
        System.out.println("Máquina 2: " + machine2Type);
        
        PoobkemonGUI gui = (PoobkemonGUI) parentFrame;
        gui.startMachineVsMachineBattle(machine1Type, machine2Type);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fondo para la pantalla
        Image backgroundImage = new ImageIcon("Poobkemon/mult/fondo2.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}