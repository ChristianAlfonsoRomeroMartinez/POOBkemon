package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleMachineVsMachine extends ShowBattle {
    private Random random = new Random();
    private Timer autoplayTimer;
    private JButton nextMoveButton;
    private JButton pauseResumeButton;
    private boolean isPaused = false;
    
    public BattleMachineVsMachine(List<String> player1Pokemon, List<String> player2Pokemon, 
                                 String player1Name, String player2Name,
                                 Poobkemon poobkemon, PoobkemonGUI gui) {
        super(player1Pokemon, player2Pokemon, player1Name, player2Name, poobkemon, gui);
        
        // Establecer el fondo específico para Machine vs Machine
        setSpecificBackground("Poobkemon/mult/fondo3.jpeg");
        
        completeInitialization();
    }

    @Override
    protected JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Panel para botones de control
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        controlPanel.setOpaque(false);
        
        // Botón para avanzar manualmente
        nextMoveButton = new JButton("Siguiente Movimiento");
        nextMoveButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextMoveButton.addActionListener(e -> executeNextMachineMove());
        
        // Botón para pausar/reanudar
        pauseResumeButton = new JButton("Pausar");
        pauseResumeButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseResumeButton.addActionListener(e -> togglePause());
        
        // Botón para volver al menú
        JButton backToMenuButton = new JButton("Volver al Menú");
        backToMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
        backToMenuButton.addActionListener(e -> {
            if (autoplayTimer != null && autoplayTimer.isRunning()) {
                autoplayTimer.stop();
            }
            gui.returnToMainMenu();
        });
        
        controlPanel.add(nextMoveButton);
        controlPanel.add(pauseResumeButton);
        controlPanel.add(backToMenuButton);
        
        // Panel informativo
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        
        JLabel machineVsMachineLabel = new JLabel("Simulación de Batalla: Máquina vs Máquina", SwingConstants.CENTER);
        machineVsMachineLabel.setFont(new Font("Arial", Font.BOLD, 16));
        machineVsMachineLabel.setForeground(Color.WHITE);
        
        JLabel turnLabel = new JLabel("Turno de: " + (isPlayer1Turn ? player1Name : player2Name), SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        turnLabel.setForeground(Color.WHITE);
        
        infoPanel.add(machineVsMachineLabel);
        infoPanel.add(turnLabel);
        
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(controlPanel, BorderLayout.CENTER);
        
        // Iniciar reproducción automática
        startAutoplay();
        
        return panel;
    }
    
    private void startAutoplay() {
        autoplayTimer = new Timer(3000, e -> executeNextMachineMove());
        autoplayTimer.start();
    }
    
    private void togglePause() {
        if (isPaused) {
            // Reanudar
            autoplayTimer.start();
            pauseResumeButton.setText("Pausar");
        } else {
            // Pausar
            autoplayTimer.stop();
            pauseResumeButton.setText("Reanudar");
        }
        isPaused = !isPaused;
    }

    private void executeNextMachineMove() {
        if (poobkemon.getBattleArena().isBattleFinished()) {
            handleBattleEnd();
            return;
        }

        // Determinar qué jugador es la máquina actual
        List<String> currentMachinePokemonList = isPlayer1Turn ? player1Pokemon : player2Pokemon;
        JPanel currentMachinePanel = isPlayer1Turn ? player1Panel : player2Panel;
        String currentMachineName = isPlayer1Turn ? player1Name : player2Name;

        // Definir al oponente
        List<String> opponentPokemonList = isPlayer1Turn ? player2Pokemon : player1Pokemon;
        JPanel opponentPanel = isPlayer1Turn ? player2Panel : player1Panel;
        String opponentName = isPlayer1Turn ? player2Name : player1Name;

        // Obtener el Pokémon actual
        String currentPokemon = (String) currentMachinePanel.getClientProperty("pokemonName");
        if (currentPokemon == null && !currentMachinePokemonList.isEmpty()) {
            currentPokemon = currentMachinePokemonList.get(0);
            currentMachinePanel.putClientProperty("pokemonName", currentPokemon);
        }

        // Decidir si cambiar de Pokémon (puedes mejorar la lógica usando el dominio)
        boolean shouldSwitchPokemon = random.nextInt(100) < 20;

        if (shouldSwitchPokemon && currentMachinePokemonList.size() > 1) {
            int newPokemonIndex;
            String newPokemonName;
            do {
                newPokemonIndex = random.nextInt(currentMachinePokemonList.size());
                newPokemonName = currentMachinePokemonList.get(newPokemonIndex);
            } while (newPokemonName.equals(currentPokemon));

            try {
                poobkemon.switchToPokemon(newPokemonIndex);
                updateBattlePokemonPanel(currentMachinePanel, newPokemonName, isPlayer1Turn);
                JOptionPane.showMessageDialog(this,
                    currentMachineName + " cambió a " + newPokemonName + ".",
                    "Cambio de Pokémon", JOptionPane.INFORMATION_MESSAGE);
                switchTurn();
                return;
            } catch (domain.PoobkemonException e) {
                System.out.println("Error al cambiar Pokémon: " + e.getMessage());
            }
        }

        // Si no cambia, realiza un ataque
        List<String> availableMoves = getSelectedMoves(currentMachineName, currentPokemon);
        if (availableMoves == null || availableMoves.isEmpty()) {
            availableMoves = new ArrayList<>();
            List<String> allAttacks = domain.Poobkemon.getAvailableAttacks();
            for (int i = 0; i < 4 && i < allAttacks.size(); i++) {
                availableMoves.add(allAttacks.get(random.nextInt(allAttacks.size())));
            }
            gui.getSelectedMoves().put(currentMachineName + "_" + currentPokemon, availableMoves);
        }

        if (!availableMoves.isEmpty()) {
            String selectedMove = availableMoves.get(random.nextInt(availableMoves.size()));
            try {
                int damage = poobkemon.attack(selectedMove, "");
                JProgressBar opponentHealthBar = (JProgressBar) opponentPanel.getClientProperty("healthBar");
                if (opponentHealthBar != null) {
                    int currentHealth = opponentHealthBar.getValue();
                    int newHealth = Math.max(0, currentHealth - damage);
                    opponentHealthBar.setValue(newHealth);
                    if (newHealth <= 20) opponentHealthBar.setForeground(Color.RED);
                    else if (newHealth <= 50) opponentHealthBar.setForeground(Color.YELLOW);
                    else opponentHealthBar.setForeground(Color.GREEN);
                }
                String message = currentMachineName + " usó " + selectedMove + " contra " + opponentName + "!";
                if (damage > 0) {
                    message += "\n¡Causó " + damage + " puntos de daño!";
                } else {
                    message += "\nEl ataque no fue efectivo.";
                }
                JOptionPane.showMessageDialog(this, message, "Turno de " + currentMachineName, JOptionPane.INFORMATION_MESSAGE);

                if (opponentHealthBar != null && opponentHealthBar.getValue() == 0) {
                    handlePokemonDefeated(opponentPokemonList, opponentPanel, isPlayer1Turn);
                } else {
                    switchTurn();
                }
            } catch (domain.PoobkemonException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al usar " + selectedMove + ": " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                switchTurn();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                currentMachineName + " no tiene movimientos disponibles. Pasando turno.",
                "Turno de " + currentMachineName, JOptionPane.INFORMATION_MESSAGE);
            switchTurn();
        }
    }

    private void handlePokemonDefeated(List<String> opponentPokemonList, JPanel opponentPanel, boolean isPlayerAttacking) {
        // Eliminar el Pokémon derrotado
        String defeatedPokemon = opponentPokemonList.remove(0);
        
        if (opponentPokemonList.isEmpty()) {
            // Si no quedan Pokémon, la batalla ha terminado
            String winnerName = isPlayerAttacking ? player1Name : player2Name;
            
            JOptionPane.showMessageDialog(this, 
                defeatedPokemon + " ha sido derrotado.\n" +
                winnerName + " ha ganado la batalla!", 
                "Fin de la Batalla", JOptionPane.INFORMATION_MESSAGE);
            
            // Detener el autoplay
            if (autoplayTimer != null && autoplayTimer.isRunning()) {
                autoplayTimer.stop();
            }
            
            // Regresar al menú principal
            gui.returnToMainMenu();
        } else {
            // Si quedan Pokémon, mostrar el siguiente
            String nextPokemon = opponentPokemonList.get(0);
            
            JOptionPane.showMessageDialog(this, 
                defeatedPokemon + " ha sido derrotado.\n" +
                "Enviando a " + nextPokemon + ".", 
                "Pokémon Derrotado", JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar la UI con el siguiente Pokémon
            updateBattlePokemonPanel(opponentPanel, nextPokemon, !isPlayerAttacking);
            
            // Cambiar turno
            switchTurn();
        }
    }

    private void handleBattleEnd() {
        // Detener el autoplay
        if (autoplayTimer != null && autoplayTimer.isRunning()) {
            autoplayTimer.stop();
        }
        
        // Determinar el ganador
        domain.Coach[] coaches = poobkemon.getBattleArena().getCoaches();
        String winnerMessage;
        
        if (coaches[0].areAllPokemonFainted()) {
            winnerMessage = player2Name + " ha ganado la batalla!";
        } else if (coaches[1].areAllPokemonFainted()) {
            winnerMessage = player1Name + " ha ganado la batalla!";
        } else {
            winnerMessage = "La batalla ha terminado en empate.";
        }
        
        JOptionPane.showMessageDialog(this, 
            winnerMessage + "\nVolviendo al menú principal.", 
            "Batalla Terminada", JOptionPane.INFORMATION_MESSAGE);
        
        gui.returnToMainMenu();
    }
}