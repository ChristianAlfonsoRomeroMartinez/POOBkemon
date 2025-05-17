package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleHumanVsMachine extends ShowBattle {
    // Solo se necesitan botones para el jugador humano
    private List<JButton> humanBattleMoveButtons = new ArrayList<>();
    private List<JButton> humanBattlePokeballButtons = new ArrayList<>();
    private boolean isHumanPlayer1; // Define si el humano es el jugador 1
    private Random random = new Random();

    public BattleHumanVsMachine(List<String> player1Pokemon, List<String> player2Pokemon, 
                               String player1Name, String player2Name,
                               boolean isHumanPlayer1, Poobkemon poobkemon, PoobkemonGUI gui) {
        super(player1Pokemon, player2Pokemon, player1Name, player2Name, poobkemon, gui);
        this.isHumanPlayer1 = isHumanPlayer1;
    }

    @Override
    protected JPanel createActionPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);

        JPanel humanPanel = isHumanPlayer1 ? player1Panel : player2Panel;
        List<String> humanPokemonList = isHumanPlayer1 ? player1Pokemon : player2Pokemon;

        // Panel de acciones del jugador humano
        JPanel humanActions = new JPanel(new BorderLayout());
        humanActions.setOpaque(false);
        humanActions.add(createMovesPanel(humanPokemonList, humanPanel), BorderLayout.CENTER);
        humanActions.add(createPokeballPanel(humanPokemonList, humanPanel), BorderLayout.SOUTH);

        // Panel de la máquina (informativo)
        JPanel machinePanel = new JPanel(new BorderLayout());
        machinePanel.setOpaque(false);
        
        JLabel machineLabel = new JLabel("Oponente controlado por CPU", SwingConstants.CENTER);
        machineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        machineLabel.setForeground(Color.WHITE);
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel(" ")); // Espaciador
        infoPanel.add(machineLabel);
        infoPanel.add(new JLabel(" ")); // Espaciador
        
        machinePanel.add(infoPanel, BorderLayout.CENTER);

        // Si el humano es el jugador 1, va a la izquierda, si no, a la derecha
        if (isHumanPlayer1) {
            panel.add(humanActions);
            panel.add(machinePanel);
        } else {
            panel.add(machinePanel);
            panel.add(humanActions);
        }

        updateBattleButtons();
        return panel;
    }

    private JPanel createMovesPanel(List<String> pokemonList, JPanel playerPanel) {
        JPanel movesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movesPanel.setOpaque(false);

        humanBattleMoveButtons.clear();
        
        String currentPlayer = isHumanPlayer1 ? player1Name : player2Name;
        String currentPokemon = (String) playerPanel.getClientProperty("pokemonName");
        List<String> moves = getSelectedMoves(currentPlayer, currentPokemon);

        for (int i = 0; i < 4; i++) {
            String moveName = (moves != null && i < moves.size()) ? moves.get(i) : "N/A";
            JButton moveButton = createMoveButton(moveName);
            moveButton.setEnabled((isPlayer1Turn && isHumanPlayer1) || (!isPlayer1Turn && !isHumanPlayer1));
            moveButton.addActionListener(e -> {
                // El jugador humano realiza su movimiento
                handleMoveAction(moveName, pokemonList, playerPanel, isHumanPlayer1);
            });
            movesPanel.add(moveButton);
            humanBattleMoveButtons.add(moveButton);
        }

        return movesPanel;
    }
    
    private JButton createMoveButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180, 200)); // Azul semi-transparente
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createPokeballPanel(List<String> pokemonList, JPanel playerPanel) {
        JPanel pokeballPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pokeballPanel.setOpaque(false);

        humanBattlePokeballButtons.clear();

        for (int i = 0; i < pokemonList.size(); i++) {
            String pokemonName = pokemonList.get(i);
            int index = i; // Capturar para el ActionListener
            
            JButton pokeballButton = new JButton(new ImageIcon("Poobkemon/mult/pokeball.jpeg"));
            pokeballButton.setPreferredSize(new Dimension(50, 50));
            pokeballButton.setToolTipText(pokemonName);
            pokeballButton.setEnabled((isPlayer1Turn && isHumanPlayer1) || (!isPlayer1Turn && !isHumanPlayer1));
            pokeballButton.addActionListener(e -> {
                try {
                    // Cambiar Pokémon en el dominio
                    poobkemon.switchToPokemon(index);
                    
                    // Actualizar en la UI
                    updateBattlePokemonPanel(playerPanel, pokemonName, isHumanPlayer1);
                    switchTurn();
                } catch (PoobkemonException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error al cambiar de Pokémon: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            pokeballPanel.add(pokeballButton);
            humanBattlePokeballButtons.add(pokeballButton);
        }

        return pokeballPanel;
    }

    @Override
    protected void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateBattleButtons();
        
        // Si es el turno de la máquina, realiza su movimiento automáticamente
        if ((isPlayer1Turn && !isHumanPlayer1) || (!isPlayer1Turn && isHumanPlayer1)) {
            // Dar tiempo para que el jugador vea que cambió el turno
            Timer delayTimer = new Timer(1500, e -> executeMachineMove());
            delayTimer.setRepeats(false);
            delayTimer.start();
        } else {
            // Si es el turno del humano, inicia el temporizador normal
            startTurnTimer();
        }
    }

    // Método para que la máquina realice su movimiento
    private void executeMachineMove() {
        List<String> machinePokemonList = isHumanPlayer1 ? player2Pokemon : player1Pokemon;
        JPanel machinePanel = isHumanPlayer1 ? player2Panel : player1Panel;
        
        // Obtener el Pokémon actual de la máquina
        String machinePokemon = (String) machinePanel.getClientProperty("pokemonName");
        
        // Obtener los movimientos disponibles
        String machinePlayer = isHumanPlayer1 ? player2Name : player1Name;
        List<String> availableMoves = getSelectedMoves(machinePlayer, machinePokemon);
        
        // Simulación de estrategia de máquina
        // En un sistema real, esto llamaría a la lógica de la máquina en el dominio
        if (availableMoves != null && !availableMoves.isEmpty()) {
            // Seleccionar un movimiento - podríamos usar una estrategia más compleja
            String selectedMove = availableMoves.get(random.nextInt(availableMoves.size()));
            
            // Mostrar mensaje indicando el movimiento
            JOptionPane.showMessageDialog(this, 
                "La máquina usa " + selectedMove, 
                "Turno de la máquina", JOptionPane.INFORMATION_MESSAGE);
            
            // Ejecutar el movimiento
            handleMoveAction(selectedMove, machinePokemonList, machinePanel, !isHumanPlayer1);
        } else {
            // Si no hay movimientos disponibles, simplemente cambia de turno
            JOptionPane.showMessageDialog(this, 
                "La máquina no tiene movimientos disponibles y pasa su turno.", 
                "Turno de la máquina", JOptionPane.INFORMATION_MESSAGE);
            switchTurn();
        }
    }

    @Override
    protected void updateBattleButtons() {
        // Activar los botones solo cuando es el turno del jugador humano
        boolean enableHumanButtons = (isPlayer1Turn && isHumanPlayer1) || (!isPlayer1Turn && !isHumanPlayer1);
        
        for (JButton btn : humanBattleMoveButtons) {
            btn.setEnabled(enableHumanButtons && !btn.getText().equals("N/A"));
        }
        
        for (JButton btn : humanBattlePokeballButtons) {
            btn.setEnabled(enableHumanButtons);
        }
    }

    @Override
    protected void onTimerEnd() {
        // Solo se aplica cuando es el turno del humano
        if ((isPlayer1Turn && isHumanPlayer1) || (!isPlayer1Turn && !isHumanPlayer1)) {
            JOptionPane.showMessageDialog(this, 
                "Tiempo agotado. Pierdes tu turno.", 
                "Turno", JOptionPane.WARNING_MESSAGE);
            switchTurn();
        }
    }
}
