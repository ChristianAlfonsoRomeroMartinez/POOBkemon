package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;

public class HumanVsMachineScreen extends JPanel {
    private JFrame parentFrame;
    private String playerName;
    private List<String> playerPokemon;
    private List<String> playerItems;
    private Map<String, List<String>> playerMoves;
    private String machineType;
    private GameController gameController;
    
    private JPanel battlePanel;
    private JPanel playerPanel;
    private JPanel machinePanel;
    private JPanel actionPanel;
    private JLabel statusLabel;
    
    private JButton[] moveButtons = new JButton[4];
    private JButton switchButton;
    private JButton itemButton;
    private JButton runButton;
    
    private Timer battleTimer;
    private boolean battleInProgress = true;

    /**
     * Constructor para la pantalla de batalla Humano vs Máquina
     * @param parentFrame Frame principal
     * @param playerName Nombre del jugador
     * @param playerPokemon Lista de Pokémon del jugador
     * @param playerItems Lista de ítems del jugador
     * @param playerMoves Mapa de movimientos para cada Pokémon
     * @param machineType Tipo de máquina ("defensive" o "attacking")
     */
    public HumanVsMachineScreen(JFrame parentFrame, String playerName, 
                                List<String> playerPokemon, List<String> playerItems,
                                Map<String, List<String>> playerMoves, String machineType) {
        this.parentFrame = parentFrame;
        this.playerName = playerName;
        this.playerPokemon = playerPokemon;
        this.playerItems = playerItems;
        this.playerMoves = playerMoves;
        this.machineType = machineType;
        
        // Inicializar el controlador del juego
        initializeGame();
        
        // Configurar la pantalla de batalla
        setLayout(new BorderLayout());
        setupBattleInterface();
    }

    /**
     * Inicializa el juego y la máquina
     */
    private void initializeGame() {
        gameController = new GameController();
        
        // Seleccionar pokémon para la máquina (entre 4-6 pokémon aleatorios)
        List<String> machinePokemon = selectRandomPokemon(6);
        
        try {
            // Convertir los movimientos a un formato adecuado para el controlador (String[][])
            String[][] movesArray = convertMovesToArray();
            
            // Inicializar el juego con el jugador humano y la máquina
            gameController.initializeGame(playerName, machineType, playerPokemon, machinePokemon);
            
            // Asignar movimientos a los pokémon del jugador
            assignPlayerMoves();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al inicializar el juego: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Convierte el mapa de movimientos a un array
     */
    private String[][] convertMovesToArray() {
        String[][] movesArray = new String[playerPokemon.size()][4];
        
        for (int i = 0; i < playerPokemon.size(); i++) {
            String pokemon = playerPokemon.get(i);
            List<String> moves = playerMoves.get(pokemon);
            
            if (moves != null) {
                for (int j = 0; j < 4 && j < moves.size(); j++) {
                    movesArray[i][j] = moves.get(j);
                }
            }
        }
        
        return movesArray;
    }
    
    /**
     * Asigna los movimientos a los pokémon del jugador
     */
    private void assignPlayerMoves() {
        Trainer humanTrainer = gameController.getHumanTrainer();
        List<Pokemon> pokemonTeam = humanTrainer.getPokemonTeam();
        
        for (int i = 0; i < pokemonTeam.size(); i++) {
            Pokemon pokemon = pokemonTeam.get(i);
            String pokemonName = pokemon.getName();
            List<String> moves = playerMoves.get(pokemonName);
            
            if (moves != null) {
                // Limpiar ataques existentes
                pokemon.getAtaques().clear();
                
                // Añadir nuevos ataques
                for (String moveName : moves) {
                    try {
                        Attack attack = AttackFactory.createAttack(moveName);
                        pokemon.addAttack(attack);
                    } catch (Exception e) {
                        System.err.println("Error al crear ataque " + moveName + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Configura la interfaz de batalla
     */
    private void setupBattleInterface() {
        // Panel principal de batalla
        battlePanel = new JPanel(new BorderLayout());
        battlePanel.setOpaque(false);
        
        // Área de visualización de Pokémon
        JPanel pokemonArea = new JPanel(new GridLayout(1, 2, 20, 0));
        pokemonArea.setOpaque(false);
        
        // Panel del jugador
        playerPanel = createPokemonPanel(gameController.getHumanTrainer().getActivePokemon(), true);
        
        // Panel de la máquina
        machinePanel = createPokemonPanel(gameController.getMachineTrainer().getActivePokemon(), false);
        
        pokemonArea.add(machinePanel);
        pokemonArea.add(playerPanel);
        
        // Panel de acciones (ataques, cambio, ítems, huir)
        actionPanel = createActionPanel();
        
        // Panel de estado para mensajes
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);
        statusLabel = new JLabel("¡Comienza la batalla! ¿Qué harás?");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        
        // Añadir todo al panel de batalla
        battlePanel.add(pokemonArea, BorderLayout.CENTER);
        battlePanel.add(actionPanel, BorderLayout.SOUTH);
        battlePanel.add(statusPanel, BorderLayout.NORTH);
        
        add(battlePanel, BorderLayout.CENTER);
        
        // Iniciar la batalla
        updateBattleState();
    }

    /**
     * Crea el panel para mostrar un Pokémon
     */
    private JPanel createPokemonPanel(Pokemon pokemon, boolean isPlayer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Imagen del Pokémon (cambia las rutas según tu estructura de archivos)
        String imagePath = isPlayer ? 
            "Poobkemon/mult/git/" + pokemon.getName().toLowerCase() + "back.gif" :
            "Poobkemon/mult/git/" + pokemon.getName().toLowerCase() + "front.gif";
        
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Información del Pokémon
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(pokemon.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        
        // Barra de PS
        JProgressBar hpBar = new JProgressBar(0, pokemon.getTotalPs());
        hpBar.setValue(pokemon.getPs());
        hpBar.setForeground(new Color(0, 200, 0));
        hpBar.setStringPainted(true);
        hpBar.setString("PS: " + pokemon.getPs() + "/" + pokemon.getTotalPs());
        
        JLabel typeLabel = new JLabel("Tipo: " + pokemon.getType());
        typeLabel.setForeground(Color.WHITE);
        
        infoPanel.add(nameLabel);
        infoPanel.add(hpBar);
        infoPanel.add(typeLabel);
        
        panel.add(pokemonImage, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    /**
     * Crea el panel de acciones para el jugador
     */
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel para los ataques
        JPanel attacksPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        attacksPanel.setOpaque(false);
        
        // Obtener los ataques del Pokémon activo
        Pokemon activePokemon = gameController.getHumanTrainer().getActivePokemon();
        List<Attack> attacks = activePokemon.getAtaques();
        
        // Crear botones para cada ataque
        for (int i = 0; i < 4; i++) {
            if (i < attacks.size()) {
                moveButtons[i] = createBattleButton(attacks.get(i).getName());
                final int index = i;
                moveButtons[i].addActionListener(e -> handleAttack(attacks.get(index)));
            } else {
                moveButtons[i] = createBattleButton("-");
                moveButtons[i].setEnabled(false);
            }
            attacksPanel.add(moveButtons[i]);
        }
        
        // Panel para otras acciones
        JPanel otherActionsPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        otherActionsPanel.setOpaque(false);
        
        switchButton = createBattleButton("Cambiar");
        switchButton.addActionListener(e -> handleSwitch());
        
        itemButton = createBattleButton("Ítem");
        itemButton.addActionListener(e -> handleItem());
        
        runButton = createBattleButton("Huir");
        runButton.addActionListener(e -> handleRun());
        
        otherActionsPanel.add(switchButton);
        otherActionsPanel.add(itemButton);
        otherActionsPanel.add(runButton);
        
        panel.add(attacksPanel, BorderLayout.CENTER);
        panel.add(otherActionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    /**
     * Crea un botón estilizado para la batalla
     */
    private JButton createBattleButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 60, 150, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 255), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        return button;
    }

    /**
     * Maneja el ataque del jugador
     */
    private void handleAttack(Attack attack) {
        if (!gameController.isPlayerTurn() || gameController.isGameOver()) {
            return;
        }
        
        try {
            String result = gameController.processPlayerTurn("attack", attack.getName());
            statusLabel.setText(result);
            
            // Actualizar la interfaz después del ataque
            updateBattleState();
            
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Maneja el cambio de Pokémon
     */
    private void handleSwitch() {
        if (!gameController.isPlayerTurn() || gameController.isGameOver()) {
            return;
        }
        
        // Crear un diálogo para seleccionar el Pokémon
        JDialog switchDialog = new JDialog(parentFrame, "Cambiar Pokémon", true);
        switchDialog.setSize(400, 300);
        switchDialog.setLocationRelativeTo(parentFrame);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Lista de Pokémon disponibles
        DefaultListModel<String> pokemonModel = new DefaultListModel<>();
        List<Pokemon> teamPokemon = gameController.getHumanTrainer().getPokemonTeam();
        
        for (int i = 0; i < teamPokemon.size(); i++) {
            Pokemon pokemon = teamPokemon.get(i);
            if (pokemon.getPs() > 0) {
                pokemonModel.addElement(i + ": " + pokemon.getName() + " (PS: " + pokemon.getPs() + "/" + pokemon.getTotalPs() + ")");
            } else {
                pokemonModel.addElement(i + ": " + pokemon.getName() + " (Debilitado)");
            }
        }
        
        JList<String> pokemonList = new JList<>(pokemonModel);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(pokemonList);
        
        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(e -> {
            int selectedIndex = pokemonList.getSelectedIndex();
            if (selectedIndex != -1) {
                try {
                    // Procesar el cambio de Pokémon
                    String result = gameController.processPlayerTurn("switch", String.valueOf(selectedIndex));
                    statusLabel.setText(result);
                    
                    // Actualizar la interfaz después del cambio
                    updateBattleState();
                    
                    switchDialog.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(switchDialog, 
                        "Error al cambiar Pokémon: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> switchDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(selectButton);
        
        dialogPanel.add(scrollPane, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        switchDialog.add(dialogPanel);
        switchDialog.setVisible(true);
    }

    /**
     * Maneja el uso de ítems
     */
    private void handleItem() {
        if (!gameController.isPlayerTurn() || gameController.isGameOver()) {
            return;
        }
        
        if (playerItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No tienes ítems disponibles.",
                "Sin ítems", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Crear un diálogo para seleccionar el ítem
        JDialog itemDialog = new JDialog(parentFrame, "Usar Ítem", true);
        itemDialog.setSize(400, 300);
        itemDialog.setLocationRelativeTo(parentFrame);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Lista de ítems disponibles
        DefaultListModel<String> itemModel = new DefaultListModel<>();
        for (String item : playerItems) {
            itemModel.addElement(item);
        }
        
        JList<String> itemList = new JList<>(itemModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(itemList);
        
        JButton selectButton = new JButton("Usar");
        selectButton.addActionListener(e -> {
            String selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                try {
                    // Procesar el uso del ítem
                    String result = gameController.processPlayerTurn("item", selectedItem);
                    statusLabel.setText(result);
                    
                    // Actualizar la interfaz después de usar el ítem
                    updateBattleState();
                    
                    // Eliminar el ítem usado de la lista
                    playerItems.remove(selectedItem);
                    
                    itemDialog.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(itemDialog, 
                        "Error al usar ítem: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> itemDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(selectButton);
        
        dialogPanel.add(scrollPane, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        itemDialog.add(dialogPanel);
        itemDialog.setVisible(true);
    }

    /**
     * Maneja la acción de huir
     */
    private void handleRun() {
        if (!gameController.isPlayerTurn() || gameController.isGameOver()) {
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que quieres huir? Perderás la batalla.",
            "Huir", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Procesar la huida
            try {
                gameController.processPlayerTurn("flee", "");
                statusLabel.setText("¡Has huido de la batalla!");
                
                // Mostrar mensaje final
                JOptionPane.showMessageDialog(this,
                    "Has huido de la batalla. La máquina gana.",
                    "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                
                // Volver al menú principal
                returnToMainMenu();
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Actualiza el estado de la batalla en la interfaz
     */
    private void updateBattleState() {
        // Verificar si el juego ha terminado
        if (gameController.isGameOver()) {
            handleGameOver();
            return;
        }
        
        // Actualizar paneles de Pokémon
        Pokemon playerActivePokemon = gameController.getHumanTrainer().getActivePokemon();
        Pokemon machineActivePokemon = gameController.getMachineTrainer().getActivePokemon();
        
        // Actualizar información de Pokémon
        updatePokemonPanel(playerPanel, playerActivePokemon, true);
        updatePokemonPanel(machinePanel, machineActivePokemon, false);
        
        // Actualizar botones de ataque
        List<Attack> attacks = playerActivePokemon.getAtaques();
        for (int i = 0; i < 4; i++) {
            if (i < attacks.size()) {
                moveButtons[i].setText(attacks.get(i).getName());
                moveButtons[i].setEnabled(gameController.isPlayerTurn());
            } else {
                moveButtons[i].setText("-");
                moveButtons[i].setEnabled(false);
            }
        }
        
        // Actualizar estado de botones según el turno
        boolean isPlayerTurn = gameController.isPlayerTurn();
        switchButton.setEnabled(isPlayerTurn);
        itemButton.setEnabled(isPlayerTurn && !playerItems.isEmpty());
        runButton.setEnabled(isPlayerTurn);
        
        // Si es turno de la máquina, procesar automáticamente
        if (!isPlayerTurn && battleInProgress) {
            // Añadir un pequeño retraso para que el jugador pueda ver lo que sucede
            Timer machineTimer = new Timer(1500, e -> {
                String result = processMachineTurn();
                statusLabel.setText(result);
                
                // Actualizar la interfaz después del turno de la máquina
                updateBattleState();
            });
            machineTimer.setRepeats(false);
            machineTimer.start();
        }
    }

    /**
     * Actualiza la información de un panel de Pokémon
     */
    private void updatePokemonPanel(JPanel panel, Pokemon pokemon, boolean isPlayer) {
        panel.removeAll();
        
        // Imagen del Pokémon
        String imagePath = isPlayer ? 
            "Poobkemon/mult/git/" + pokemon.getName().toLowerCase() + "back.gif" :
            "Poobkemon/mult/git/" + pokemon.getName().toLowerCase() + "front.gif";
        
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Información del Pokémon
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(pokemon.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        
        // Barra de PS
        JProgressBar hpBar = new JProgressBar(0, pokemon.getTotalPs());
        hpBar.setValue(pokemon.getPs());
        hpBar.setForeground(new Color(0, 200, 0));
        hpBar.setStringPainted(true);
        hpBar.setString("PS: " + pokemon.getPs() + "/" + pokemon.getTotalPs());
        
        JLabel typeLabel = new JLabel("Tipo: " + pokemon.getType());
        typeLabel.setForeground(Color.WHITE);
        
        infoPanel.add(nameLabel);
        infoPanel.add(hpBar);
        infoPanel.add(typeLabel);
        
        panel.setLayout(new BorderLayout());
        panel.add(pokemonImage, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Procesa el turno de la máquina
     */
    private String processMachineTurn() {
        try {
            return gameController.processMachineTurn();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el turno de la máquina: " + e.getMessage();
        }
    }

    /**
     * Maneja el fin del juego
     */
    private void handleGameOver() {
        battleInProgress = false;
        
        // Desactivar todos los botones
        for (JButton button : moveButtons) {
            button.setEnabled(false);
        }
        switchButton.setEnabled(false);
        itemButton.setEnabled(false);
        runButton.setEnabled(false);
        
        // Mostrar resultado
        String result = gameController.getGameResult();
        statusLabel.setText(result);
        
        // Mostrar cuadro de diálogo con resultado
        JOptionPane.showMessageDialog(this, result, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        
        // Añadir botón para volver al menú principal
        JButton returnButton = new JButton("Volver al menú principal");
        returnButton.addActionListener(e -> returnToMainMenu());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(returnButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
    }

    /**
     * Selecciona Pokémon aleatorios de la lista disponible
     */
    private List<String> selectRandomPokemon(int count) {
        List<String> allPokemon = Poobkemon.getAvailablePokemon();
        List<String> selected = new ArrayList<>();
        
        // Si hay menos Pokémon disponibles que los solicitados, ajustar
        int actualCount = Math.min(count, allPokemon.size());
        
        // Seleccionar Pokémon aleatoriamente
        java.util.Random random = new java.util.Random();
        List<String> tempList = new ArrayList<>(allPokemon);
        
        for (int i = 0; i < actualCount; i++) {
            int randomIndex = random.nextInt(tempList.size());
            selected.add(tempList.get(randomIndex));
            tempList.remove(randomIndex);
        }
        
        return selected;
    }

    /**
     * Vuelve al menú principal
     */
    private void returnToMainMenu() {
        parentFrame.getContentPane().removeAll();
        parentFrame.setContentPane(new PoobkemonGUI());
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Pintar fondo de batalla
        Image backgroundImage = new ImageIcon("Poobkemon/mult/fondo3.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}