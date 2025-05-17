package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ShowBattle extends JPanel {
    // Datos básicos de la batalla
    protected List<String> player1Pokemon;
    protected List<String> player2Pokemon;
    protected String player1Name;
    protected String player2Name;
    protected boolean isPlayer1Turn = true;
    
    // Paneles y componentes de la UI
    protected JPanel player1Panel;
    protected JPanel player2Panel;
    protected JLabel turnTimerLabel;
    
    // Control de tiempo
    protected Timer turnTimer;
    protected int turnTimeRemaining = 20; // segundos por turno
    
    // Referencia al dominio y GUI
    protected Poobkemon poobkemon;
    protected PoobkemonGUI gui;
    
    // Mapeo de movimientos seleccionados (key: player_pokemon, value: lista de movimientos)
    protected Map<String, List<String>> selectedMoves = new HashMap<>();

    /**
     * Constructor para la pantalla de batalla
     */
    public ShowBattle(List<String> player1Pokemon, List<String> player2Pokemon, 
                      String player1Name, String player2Name,
                      Poobkemon poobkemon, PoobkemonGUI gui) {
        this.player1Pokemon = new ArrayList<>(player1Pokemon);
        this.player2Pokemon = new ArrayList<>(player2Pokemon);
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.poobkemon = poobkemon;
        this.gui = gui;
        
        // Inicializa los movimientos desde el dominio
        initializeSelectedMoves();
        
        // Configura la pantalla
        setupBattleScreen();
    }
    
    /**
     * Inicializa los movimientos desde el dominio
     */
    protected void initializeSelectedMoves() {
        if (poobkemon != null) {
            // Obtener los movimientos de Survival si estamos en ese modo
            Map<String, String[][]> survivalMoves = poobkemon.getSurvivalMoves();
            if (!survivalMoves.isEmpty()) {
                processSurvivalMoves(survivalMoves);
                return;
            }
            
            // Si no es survival, intentar obtener los movimientos del dominio
            try {
                // Aquí deberías obtener los movimientos del BattleArena
                // Esto varía según cómo esté estructurado tu dominio
                if (gui != null) {
                    // Recuperar los movimientos ya seleccionados en la GUI
                    selectedMoves = new HashMap<>(gui.getSelectedMoves());
                }
            } catch (Exception e) {
                System.err.println("Error al inicializar movimientos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Procesa los movimientos en modo Survival
     */
    private void processSurvivalMoves(Map<String, String[][]> survivalMoves) {
        // Procesar los movimientos del jugador 1
        String[][] player1Moves = survivalMoves.get("Player 1");
        if (player1Moves != null) {
            for (int i = 0; i < player1Pokemon.size() && i < player1Moves.length; i++) {
                List<String> moves = new ArrayList<>();
                for (int j = 0; j < player1Moves[i].length; j++) {
                    if (player1Moves[i][j] != null) {
                        moves.add(player1Moves[i][j]);
                    }
                }
                selectedMoves.put(player1Name + "_" + player1Pokemon.get(i), moves);
            }
        }
        
        // Procesar los movimientos del jugador 2
        String[][] player2Moves = survivalMoves.get("Player 2");
        if (player2Moves != null) {
            for (int i = 0; i < player2Pokemon.size() && i < player2Moves.length; i++) {
                List<String> moves = new ArrayList<>();
                for (int j = 0; j < player2Moves[i].length; j++) {
                    if (player2Moves[i][j] != null) {
                        moves.add(player2Moves[i][j]);
                    }
                }
                selectedMoves.put(player2Name + "_" + player2Pokemon.get(i), moves);
            }
        }
    }

    /**
     * Configura la pantalla de batalla principal
     */
    protected void setupBattleScreen() {
        setLayout(new BorderLayout());
        
        // Cronómetro en la parte superior
        turnTimerLabel = new JLabel("Tiempo restante: " + turnTimeRemaining + " segundos", SwingConstants.CENTER);
        turnTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnTimerLabel.setForeground(Color.WHITE);
        add(turnTimerLabel, BorderLayout.NORTH);
        
        // Panel central con los Pokémon
        JPanel battlePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        battlePanel.setOpaque(false);
        player1Panel = createBattlePokemonPanel(player1Pokemon.get(0), true);
        player2Panel = createBattlePokemonPanel(player2Pokemon.get(0), false);
        battlePanel.add(player1Panel);
        battlePanel.add(player2Panel);
        add(battlePanel, BorderLayout.CENTER);
        
        // Panel de acciones (botones)
        add(createActionPanel(), BorderLayout.SOUTH);
        
        // Iniciar temporizador
        startTurnTimer();
    }

    /**
     * Crea el panel para mostrar un Pokémon en batalla
     */
    protected JPanel createBattlePokemonPanel(String pokemonName, boolean isPlayer1) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Barra de vida
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        panel.add(healthBar, BorderLayout.NORTH);

        // Imagen del Pokémon
        String imagePath = "Poobkemon/mult/git/" + pokemonName.toLowerCase() + (isPlayer1 ? "back.gif" : "front.gif");
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImage.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(pokemonImage, BorderLayout.CENTER);

        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(pokemonName, SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLabel, BorderLayout.SOUTH);

        panel.putClientProperty("healthBar", healthBar);
        panel.putClientProperty("pokemonName", pokemonName);
        return panel;
    }
    
    /**
     * Actualiza la visualización de un Pokémon (cuando cambia o recibe daño)
     */
    protected void updateBattlePokemonPanel(JPanel panel, String pokemonName, boolean isPlayer1) {
        // Obtener la barra de vida existente
        JProgressBar existingHealthBar = (JProgressBar) panel.getClientProperty("healthBar");
        int currentHealth = 100;
        if (existingHealthBar != null) {
            currentHealth = existingHealthBar.getValue();
        }
        
        panel.removeAll();

        // Actualizar la imagen
        String imagePath = "Poobkemon/mult/git/" + pokemonName.toLowerCase() + (isPlayer1 ? "back.gif" : "front.gif");
        JLabel pokemonImage = new JLabel(new ImageIcon(imagePath));
        pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImage.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(pokemonImage, BorderLayout.CENTER);

        // Actualizar la barra de vida
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(currentHealth);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        panel.add(healthBar, BorderLayout.NORTH);
        
        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(pokemonName, SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLabel, BorderLayout.SOUTH);

        // Guardar referencias
        panel.putClientProperty("healthBar", healthBar);
        panel.putClientProperty("pokemonName", pokemonName);

        panel.revalidate();
        panel.repaint();
    }
    
    /**
     * Método para manejar la acción de un movimiento
     */
    protected void handleMoveAction(String moveName, List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
        if ("N/A".equals(moveName)) {
            JOptionPane.showMessageDialog(this, "Movimiento no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        stopTurnTimer();

        // Obtener información del oponente
        List<String> opponentPokemonList = isPlayer1 ? player2Pokemon : player1Pokemon;
        JPanel opponentPanel = isPlayer1 ? player2Panel : player1Panel;
        String attackerPokemon = (String) playerPanel.getClientProperty("pokemonName");
        String targetPokemon = (String) opponentPanel.getClientProperty("pokemonName");

        try {
            // Aplicar movimiento en el dominio (simplificado para este ejemplo)
            poobkemon.attack(moveName, isPlayer1 ? "Player 1" : "Player 2");
            
            // Simular daño (esto se debe reemplazar con el daño real del dominio)
            int damage = (int)(Math.random() * 30) + 10;
            
            // Actualizar la UI con el resultado
            JProgressBar opponentHealthBar = (JProgressBar) opponentPanel.getClientProperty("healthBar");
            int newHealth = Math.max(0, opponentHealthBar.getValue() - damage);
            opponentHealthBar.setValue(newHealth);

            if (newHealth == 0) {
                handlePokemonDefeated(opponentPokemonList, opponentPanel, isPlayer1);
            } else {
                switchTurn();
            }
        } catch (PoobkemonException e) {
            JOptionPane.showMessageDialog(this, "Error al aplicar movimiento: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            switchTurn();
        }
    }
    
    /**
     * Maneja cuando un Pokémon es derrotado
     */
    protected void handlePokemonDefeated(List<String> opponentPokemonList, JPanel opponentPanel, boolean isPlayerAttacking) {
        String defeatedPokemon = opponentPokemonList.get(0);
        JOptionPane.showMessageDialog(this, defeatedPokemon + " ha sido derrotado.", 
            "Batalla", JOptionPane.INFORMATION_MESSAGE);
        
        // Eliminar el Pokémon derrotado
        opponentPokemonList.remove(0);
        
        // Verificar si hay más Pokémon
        if (opponentPokemonList.isEmpty()) {
            // Victoria
            String winner = isPlayerAttacking ? player1Name : player2Name;
            JOptionPane.showMessageDialog(this, winner + " ha ganado la batalla!", 
                "Fin de la Batalla", JOptionPane.INFORMATION_MESSAGE);
            
            // Regresar al menú principal
            gui.returnToMainMenu();
            return;
        }
        
        // Actualizar con el siguiente Pokémon
        updateBattlePokemonPanel(opponentPanel, opponentPokemonList.get(0), !isPlayerAttacking);
        
        // Cambiar de turno
        switchTurn();
    }
    
    /**
     * Obtiene los movimientos seleccionados para un Pokémon
     */
    protected List<String> getSelectedMoves(String player, String pokemon) {
        List<String> moves = selectedMoves.get(player + "_" + pokemon);
        return moves != null ? moves : new ArrayList<>();
    }
    
    /**
     * Inicia/reinicia el temporizador de turno
     */
    protected void startTurnTimer() {
        if (turnTimer != null) {
            turnTimer.stop();
        }

        turnTimeRemaining = 20;
        updateTimerLabel();

        turnTimer = new Timer(1000, e -> {
            turnTimeRemaining--;
            updateTimerLabel();
            if (turnTimeRemaining <= 0) {
                turnTimer.stop();
                onTimerEnd();
            }
        });

        turnTimer.start();
    }
    
    /**
     * Detiene el temporizador
     */
    protected void stopTurnTimer() {
        if (turnTimer != null) {
            turnTimer.stop();
        }
    }
    
    /**
     * Actualiza la etiqueta del temporizador
     */
    protected void updateTimerLabel() {
        turnTimerLabel.setText("Tiempo restante: " + turnTimeRemaining + " segundos");
    }
    
    /**
     * Cambia el turno entre jugadores
     */
    protected void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateBattleButtons();
        startTurnTimer();
    }
    
    /**
     * Actualiza los botones según el turno actual
     * Debe ser implementado por las subclases
     */
    protected abstract void updateBattleButtons();
    
    /**
     * Acción cuando se agota el tiempo del turno
     */
    protected abstract void onTimerEnd();
    
    /**
     * Crea el panel de acciones específico para cada tipo de batalla
     */
    protected abstract JPanel createActionPanel();
}
