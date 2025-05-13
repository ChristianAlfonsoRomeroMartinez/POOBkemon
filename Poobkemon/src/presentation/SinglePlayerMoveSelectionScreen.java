package presentation;

import domain.Poobkemon;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class SinglePlayerMoveSelectionScreen extends JPanel {

    private JFrame parentFrame;
    private String playerName;
    private List<String> selectedPokemon;
    private List<String> selectedItems;
    private Map<String, List<String>> selectedMoves = new HashMap<>();
    private String gameType;

    public SinglePlayerMoveSelectionScreen(JFrame parentFrame, String playerName, List<String> selectedPokemon, 
                                          List<String> selectedItems, String gameType) {
        this.parentFrame = parentFrame;
        this.playerName = playerName;
        this.selectedPokemon = selectedPokemon;
        this.selectedItems = selectedItems;
        this.gameType = gameType;
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Selecciona los movimientos para tus Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de selección de Pokémon
        JPanel pokemonGrid = new JPanel(new GridLayout(2, 3, 10, 10));
        pokemonGrid.setOpaque(false);

        for (String pokemon : selectedPokemon) {
            // Ruta de la imagen
            String imagePath = "Poobkemon/mult/" + pokemon.toLowerCase() + "Front.png";
            ImageIcon icon = new ImageIcon(imagePath);

            JButton btn = new JButton(icon);
            btn.setPreferredSize(new Dimension(100, 100));
            btn.addActionListener(e -> showMoveSelectionDialog(pokemon));

            // Ajustar imagen al tamaño del botón
            if (icon.getIconWidth() > 0) {
                btn.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                    80, 80, Image.SCALE_SMOOTH)));
            }
            
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            pokemonGrid.add(btn);
        }

        // Panel central con la cuadrícula de Pokémon
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(new JLabel("Haz clic en un Pokémon para seleccionar sus movimientos:", SwingConstants.CENTER), BorderLayout.NORTH);
        centerPanel.add(pokemonGrid, BorderLayout.CENTER);

        // Botones de navegación
        JButton finishButton = createMenuButton("Finalizar", e -> {
            // Verificar que todos los Pokémon tengan movimientos seleccionados
            for (String pokemon : selectedPokemon) {
                List<String> moves = selectedMoves.get(pokemon);
                if (moves == null || moves.size() < 4) {
                    JOptionPane.showMessageDialog(this, 
                        "Todos los Pokémon deben tener 4 movimientos seleccionados.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            printFinalSelection();
            
            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(this, 
                "¡Selección completada! Estás listo para jugar contra la máquina.",
                "Listo para jugar", JOptionPane.INFORMATION_MESSAGE);
                
            // Aquí se puede implementar la lógica para iniciar la batalla
        });

        JButton backButton = createMenuButton("Atrás", e -> {
            // Regresar a la selección de ítems
            parentFrame.getContentPane().removeAll();
            parentFrame.setContentPane(new SinglePlayerItemSelectionScreen(parentFrame, playerName, selectedPokemon, gameType));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        navigationPanel.setOpaque(false);
        navigationPanel.add(backButton);
        navigationPanel.add(finishButton);

        // Ensamblar todo
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(navigationPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
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

    private void showMoveSelectionDialog(String pokemon) {
        JDialog dialog = new JDialog(parentFrame, "Seleccionar movimientos para " + pokemon, true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(parentFrame);

        // Panel principal con imagen de fondo
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("Poobkemon/mult/fondoelecion.jpeg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Imagen del Pokémon
        String imagePath = "Poobkemon/mult/git/" + pokemon.toLowerCase() + "front.gif";
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImage.setPreferredSize(new Dimension(120, 120));
        mainPanel.add(pokemonImage, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Selecciona 4 movimientos para " + pokemon, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
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
        moveList.setFont(new Font("Arial", Font.PLAIN, 14));
        moveList.setBackground(new Color(0, 51, 102, 150));
        moveList.setForeground(new Color(204, 255, 204));
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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(typePanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Lista para mostrar los movimientos seleccionados
        DefaultListModel<String> selectedMoveModel = new DefaultListModel<>();
        JList<String> selectedMoveList = new JList<>(selectedMoveModel);
        selectedMoveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedMoveList.setVisibleRowCount(4);
        selectedMoveList.setFont(new Font("Arial", Font.PLAIN, 14));
        selectedMoveList.setBackground(new Color(0, 51, 102, 150));
        selectedMoveList.setForeground(new Color(204, 255, 102));
        selectedMoveList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        JScrollPane selectedScrollPane = new JScrollPane(selectedMoveList);
        selectedScrollPane.setOpaque(false);
        selectedScrollPane.getViewport().setOpaque(false);

        // Cargar movimientos previamente seleccionados (si existen)
        List<String> existingMoves = selectedMoves.get(pokemon);
        if (existingMoves != null) {
            existingMoves.forEach(selectedMoveModel::addElement);
        }

        // Botón "Agregar"
        JButton addButton = createCrystalButton("Agregar");
        addButton.addActionListener(e -> {
            String selectedMove = moveList.getSelectedValue();
            if (selectedMove != null) {
                if (selectedMoveModel.size() < 4) {
                    selectedMoveModel.addElement(selectedMove);
                } else {
                    JOptionPane.showMessageDialog(dialog, "No puedes agregar más de 4 movimientos.", 
                                               "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecciona un movimiento para agregar.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón "Eliminar"
        JButton removeButton = createCrystalButton("Eliminar");
        removeButton.addActionListener(e -> {
            int selectedIndex = selectedMoveList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedMoveModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecciona un movimiento para eliminar.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón "Confirmar"
        JButton confirmButton = createCrystalButton("Confirmar");
        confirmButton.addActionListener(e -> {
            if (selectedMoveModel.size() == 4) {
                List<String> selectedMovesList = new ArrayList<>();
                for (int i = 0; i < selectedMoveModel.size(); i++) {
                    selectedMovesList.add(selectedMoveModel.getElementAt(i));
                }

                // Guardar los movimientos seleccionados
                selectedMoves.put(pokemon, selectedMovesList);
                System.out.println("Movimientos seleccionados para " + pokemon + ": " + selectedMovesList);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Debes seleccionar exactamente 4 movimientos.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(confirmButton);

        // Panel inferior con lista de seleccionados y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(selectedScrollPane, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.getContentPane().add(mainPanel);
        dialog.setVisible(true);
    }

    private JButton createCrystalButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo translúcido con gradiente
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 139, 230, 180),
                    0, getHeight(), new Color(50, 205, 50, 180)
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

    private void printFinalSelection() {
        System.out.println("\n=== SELECCIÓN FINAL DEL JUGADOR ===");
        System.out.println("Jugador: " + playerName);
        System.out.println("Tipo de máquina: " + gameType);
        
        System.out.println("\nPokémon seleccionados:");
        for (String pokemon : selectedPokemon) {
            System.out.println("- " + pokemon);
            List<String> moves = selectedMoves.get(pokemon);
            if (moves != null) {
                System.out.println("  Movimientos: " + String.join(", ", moves));
            }
        }
        
        System.out.println("\nÍtems seleccionados:");
        if (selectedItems.isEmpty()) {
            System.out.println("- Ninguno");
        } else {
            for (String item : selectedItems) {
                System.out.println("- " + item);
            }
        }
        
        System.out.println("\n=== CONFIGURACIÓN COMPLETA ===");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fondo para la pantalla
        Image backgroundImage = new ImageIcon("Poobkemon/mult/fondo2.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}