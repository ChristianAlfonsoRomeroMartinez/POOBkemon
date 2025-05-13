package presentation;

import domain.Poobkemon;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SinglePlayerPokemonSelectionScreen extends JPanel {

    private JFrame parentFrame;
    private List<String> selectedPokemon = new ArrayList<>();
    private String playerName = "Jugador Humano";
    private String gameType; // Tipo de estrategia de máquina seleccionada

    public SinglePlayerPokemonSelectionScreen(JFrame parentFrame, String gameType) {
        this.parentFrame = parentFrame;
        this.gameType = gameType;
        setLayout(new BorderLayout());
        
        // Usar la misma imagen de fondo que en el juego principal
        setOpaque(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Selecciona tus Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de selección
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setOpaque(false);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de texto para el nombre del jugador
        JTextField nameField = new JTextField(playerName);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setBackground(new Color(0, 51, 102));
        nameField.setForeground(new Color(204, 255, 204));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        // Lista de Pokémon disponibles
        List<String> availablePokemon = Poobkemon.getAvailablePokemon();
        DefaultListModel<String> pokemonModel = new DefaultListModel<>();
        availablePokemon.forEach(pokemonModel::addElement);

        JList<String> pokemonList = new JList<>(pokemonModel);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pokemonList.setVisibleRowCount(8);
        pokemonList.setFont(new Font("Arial", Font.PLAIN, 14));
        pokemonList.setBackground(new Color(0, 51, 102));
        pokemonList.setForeground(new Color(204, 255, 204));
        pokemonList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        JScrollPane scrollPane = new JScrollPane(pokemonList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Modelo para los Pokémon seleccionados
        DefaultListModel<String> selectedPokemonModel = new DefaultListModel<>();
        JList<String> selectedPokemonList = new JList<>(selectedPokemonModel);
        selectedPokemonList.setVisibleRowCount(6);
        selectedPokemonList.setFont(new Font("Arial", Font.PLAIN, 14));
        selectedPokemonList.setBackground(new Color(0, 51, 102));
        selectedPokemonList.setForeground(new Color(204, 255, 204));
        selectedPokemonList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        JScrollPane selectedScrollPane = new JScrollPane(selectedPokemonList);
        selectedScrollPane.setOpaque(false);
        selectedScrollPane.getViewport().setOpaque(false);

        // Botón para agregar Pokémon
        JButton addButton = createMenuButton("Agregar Pokémon", e -> {
            String selected = pokemonList.getSelectedValue();
            if (selected != null && selectedPokemonModel.size() < 6) {
                selectedPokemonModel.addElement(selected);
                selectedPokemon.add(selected);
                System.out.println("Pokémon seleccionado: " + selected);
            } else if (selectedPokemonModel.size() >= 6) {
                JOptionPane.showMessageDialog(this, "Solo puedes seleccionar 6 Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón para eliminar Pokémon
        JButton removeButton = createMenuButton("Eliminar Pokémon", e -> {
            int selectedIndex = selectedPokemonList.getSelectedIndex();
            if (selectedIndex != -1) {
                String removed = selectedPokemonModel.remove(selectedIndex);
                selectedPokemon.remove(removed);
                System.out.println("Pokémon eliminado: " + removed);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un Pokémon para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel para los botones de agregar/eliminar
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Panel superior con el campo de nombre y la lista disponible
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(nameField, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con los botones y la lista de seleccionados
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(selectedScrollPane, BorderLayout.CENTER);

        // Ensamblar el panel de selección
        selectionPanel.add(topPanel, BorderLayout.NORTH);
        selectionPanel.add(bottomPanel, BorderLayout.CENTER);

        // Botones de navegación
        JButton nextButton = createMenuButton("Siguiente", e -> {
            playerName = nameField.getText().trim();
            
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (selectedPokemon.size() != 6) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente 6 Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("\nJugador " + playerName + " seleccionó los siguientes Pokémon:");
            for (String pokemon : selectedPokemon) {
                System.out.println("- " + pokemon);
            }
            
            goToItemSelectionScreen();
        });

        JButton backButton = createMenuButton("Volver", e -> {
            // Regresar a la selección de tipo de máquina
            parentFrame.getContentPane().removeAll();
            parentFrame.setContentPane(new MachineGameTypeSelectionScreen(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        navigationPanel.setOpaque(false);
        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);

        // Ensamblar todo
        mainPanel.add(selectionPanel, BorderLayout.CENTER);
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

    private void goToItemSelectionScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new SinglePlayerItemSelectionScreen(parentFrame, playerName, selectedPokemon, gameType));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fondo para la pantalla
        Image backgroundImage = new ImageIcon("Poobkemon/mult/fondo2.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}