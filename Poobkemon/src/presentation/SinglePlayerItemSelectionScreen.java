package presentation;

import domain.Poobkemon;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SinglePlayerItemSelectionScreen extends JPanel {

    private JFrame parentFrame;
    private String playerName;
    private List<String> selectedPokemon;
    private List<String> selectedItems = new ArrayList<>();
    private String gameType;

    public SinglePlayerItemSelectionScreen(JFrame parentFrame, String playerName, List<String> selectedPokemon, String gameType) {
        this.parentFrame = parentFrame;
        this.playerName = playerName;
        this.selectedPokemon = selectedPokemon;
        this.gameType = gameType;
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de la pantalla
        JLabel titleLabel = new JLabel("Selecciona tus Ítems", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de selección
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setOpaque(false);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etiqueta con el nombre del jugador
        JLabel nameLabel = new JLabel(playerName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Lista de ítems disponibles
        List<String> availableItems = Poobkemon.getAvailableItems();
        DefaultListModel<String> itemModel = new DefaultListModel<>();
        availableItems.forEach(itemModel::addElement);

        JList<String> itemList = new JList<>(itemModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setVisibleRowCount(8);
        itemList.setFont(new Font("Arial", Font.PLAIN, 14));
        itemList.setBackground(new Color(0, 51, 102));
        itemList.setForeground(new Color(204, 255, 204));
        itemList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Modelo para los ítems seleccionados
        DefaultListModel<String> selectedItemModel = new DefaultListModel<>();
        JList<String> selectedItemList = new JList<>(selectedItemModel);
        selectedItemList.setVisibleRowCount(4);
        selectedItemList.setFont(new Font("Arial", Font.PLAIN, 14));
        selectedItemList.setBackground(new Color(0, 51, 102));
        selectedItemList.setForeground(new Color(204, 255, 204));
        selectedItemList.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));

        JScrollPane selectedScrollPane = new JScrollPane(selectedItemList);
        selectedScrollPane.setOpaque(false);
        selectedScrollPane.getViewport().setOpaque(false);

        // Botón para agregar ítems
        JButton addButton = createMenuButton("Agregar Ítem", e -> {
            String selected = itemList.getSelectedValue();
            if (selected != null && selectedItemModel.size() < 4) {
                selectedItemModel.addElement(selected);
                selectedItems.add(selected);
                System.out.println("Ítem seleccionado: " + selected);
            } else if (selectedItemModel.size() >= 4) {
                JOptionPane.showMessageDialog(this, "Solo puedes seleccionar 4 ítems.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón para eliminar ítems
        JButton removeButton = createMenuButton("Eliminar Ítem", e -> {
            int selectedIndex = selectedItemList.getSelectedIndex();
            if (selectedIndex != -1) {
                String removed = selectedItemModel.remove(selectedIndex);
                selectedItems.remove(removed);
                System.out.println("Ítem eliminado: " + removed);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un ítem para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel para los botones de agregar/eliminar
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Panel superior con nombre y lista disponible
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(nameLabel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones y lista seleccionada
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(selectedScrollPane, BorderLayout.CENTER);

        // Ensamblar el panel de selección
        selectionPanel.add(topPanel, BorderLayout.NORTH);
        selectionPanel.add(bottomPanel, BorderLayout.CENTER);

        // Botones de navegación
        JButton nextButton = createMenuButton("Siguiente", e -> {
            if (selectedItems.size() > 4) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar más de 4 ítems.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("\nJugador " + playerName + " seleccionó los siguientes ítems:");
            for (String item : selectedItems) {
                System.out.println("- " + item);
            }
            
            goToMoveSelectionScreen();
        });

        JButton backButton = createMenuButton("Atrás", e -> {
            // Regresar a la selección de Pokémon
            parentFrame.getContentPane().removeAll();
            parentFrame.setContentPane(new SinglePlayerPokemonSelectionScreen(parentFrame, gameType));
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

    private void goToMoveSelectionScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new SinglePlayerMoveSelectionScreen(parentFrame, playerName, selectedPokemon, selectedItems, gameType));
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