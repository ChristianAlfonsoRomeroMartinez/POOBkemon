package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class BattleHumanVsMachine extends ShowBattle {

    private List<JButton> humanBattleMoveButtons = new ArrayList<>(); // Initialize at declaration
    private List<JButton> humanBattlePokeballButtons = new ArrayList<>(); // Initialize at declaration
    private boolean isHumanPlayer1;
    private Random random = new Random();

    public BattleHumanVsMachine(List<String> p1Pokemons, List<String> p2Pokemons,
                           String p1Name, String p2Name, // These are player1Name and player2Name for ShowBattle
                           boolean isP1Human, Poobkemon poobkemon, PoobkemonGUI gui) {
        // p1Name should be human's name, p2Name should be machine's name if isP1Human is true
        super(p1Pokemons, p2Pokemons, p1Name, p2Name, poobkemon, gui); // Calls ShowBattle constructor (base part)
        this.isHumanPlayer1 = isP1Human; // This flag determines which side is human

        // humanBattleMoveButtons and humanBattlePokeballButtons are already initialized at declaration

        System.out.println("[BattleHumanVsMachine Constructor] isHumanPlayer1: " + this.isHumanPlayer1);
        // ShowBattle fields (player1Name, player1Pokemon etc.) are initialized by super() call.
        // Let's log them here to see what BattleHumanVsMachine inherits.
        System.out.println("[BattleHumanVsMachine Constructor] Inherited this.player1Name: " + this.player1Name +
                           ", this.player2Name: " + this.player2Name);
        System.out.println("[BattleHumanVsMachine Constructor] Inherited this.player1Pokemon: " + this.player1Pokemon);

        completeInitialization(); // Call UI setup now that subclass fields are initialized
    }

    @Override
    protected JPanel createActionPanel() {
        JPanel mainActionPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainActionPanel.setOpaque(false);

        // Determine which player panel and Pokémon list belong to the human
        JPanel uiPanelForHuman;
        List<String> pokemonListOfHuman;
        String humanPlayerNameForUI;

        if (isHumanPlayer1) {
            uiPanelForHuman = player1Panel; // player1Panel is from ShowBattle, for Player 1
            pokemonListOfHuman = player1Pokemon; // player1Pokemon is from ShowBattle
            humanPlayerNameForUI = this.player1Name; // from ShowBattle
        } else {
            uiPanelForHuman = player2Panel; // player2Panel is from ShowBattle, for Player 2
            pokemonListOfHuman = player2Pokemon; // player2Pokemon is from ShowBattle
            humanPlayerNameForUI = this.player2Name; // from ShowBattle
        }
        System.out.println("[BattleHumanVsMachine.createActionPanel] Human player for UI: " + humanPlayerNameForUI);


        // Panel for Human Player's actions
        JPanel humanPlayerActionsPanel = new JPanel(new BorderLayout());
        humanPlayerActionsPanel.setOpaque(false);
        // Pass the correct Pokémon list and UI panel for the human
        humanPlayerActionsPanel.add(createMovesPanel(pokemonListOfHuman, uiPanelForHuman, humanPlayerNameForUI), BorderLayout.CENTER);
        humanPlayerActionsPanel.add(createPokeballPanel(pokemonListOfHuman, uiPanelForHuman), BorderLayout.SOUTH);

        // Placeholder Panel for Machine (no controls, just info)
        JPanel machineDisplayPanel = new JPanel(new BorderLayout());
        machineDisplayPanel.setOpaque(false);
        String machineNameForDisplay = isHumanPlayer1 ? this.player2Name : this.player1Name;
        JLabel machineLabel = new JLabel("Oponente: " + machineNameForDisplay + " (CPU)", SwingConstants.CENTER);
        machineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        machineLabel.setForeground(Color.WHITE);
        machineDisplayPanel.add(machineLabel, BorderLayout.CENTER);

        if (isHumanPlayer1) {
            mainActionPanel.add(humanPlayerActionsPanel);
            mainActionPanel.add(machineDisplayPanel);
        } else {
            mainActionPanel.add(machineDisplayPanel);
            mainActionPanel.add(humanPlayerActionsPanel);
        }
        updateBattleButtons(); // Ensure buttons are correctly enabled/disabled initially
        return mainActionPanel;
    }

    // Modified to accept the humanPlayerName explicitly for clarity in key generation
    private JPanel createMovesPanel(List<String> actualHumanPokemonList, JPanel actualHumanDisplayPanel, String actualHumanPlayerName) {
        JPanel movesPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        movesPanel.setOpaque(false);

        humanBattleMoveButtons.clear(); // Clear previous buttons

        // Get the current active Pokémon name for the human from their display panel
        String currentHumanPokemonName = (String) actualHumanDisplayPanel.getClientProperty("pokemonName");
        if (currentHumanPokemonName == null && !actualHumanPokemonList.isEmpty()) {
            currentHumanPokemonName = actualHumanPokemonList.get(0); // Fallback if property not set
             actualHumanDisplayPanel.putClientProperty("pokemonName", currentHumanPokemonName); // Ensure it's set
        }


        System.out.println("[createMovesPanel for Human] actualHumanPlayerName: " + actualHumanPlayerName +
                           ", currentHumanPokemonName: " + currentHumanPokemonName);

        // The key for selectedMoves map is "PlayerName_PokemonName"
        String keyForHumanMoves = actualHumanPlayerName + "_" + currentHumanPokemonName;
        // getSelectedMoves is inherited from ShowBattle, uses ShowBattle's 'selectedMoves' map
        List<String> moves = getSelectedMoves(actualHumanPlayerName, currentHumanPokemonName);

        System.out.println("[createMovesPanel for Human] Key used: " + keyForHumanMoves + ". Moves found: " + moves);

        if (moves == null || moves.isEmpty()) {
            System.out.println("WARNING: No moves found for " + keyForHumanMoves + ". Displaying N/A.");
            moves = new ArrayList<>(); // Ensure moves is not null for the loop
        }

        for (int i = 0; i < 4; i++) {
            String moveName = i < moves.size() ? moves.get(i) : "N/A";
            JButton moveButton = createStyledMoveButton(moveName); // Use a styling method

            // Determine if it's this human player's turn to act
            boolean isThisHumansActualTurn = (this.isPlayer1Turn && this.isHumanPlayer1) || (!this.isPlayer1Turn && !this.isHumanPlayer1);
            moveButton.setEnabled(isThisHumansActualTurn && !moveName.equals("N/A"));

            final String finalMoveName = moveName; // Capture for lambda
            moveButton.addActionListener(e -> {
                // The last boolean to handleMoveAction is 'isActorPlayer1'
                // If human is P1 (isHumanPlayer1=true), then actor is P1.
                // If human is P2 (isHumanPlayer1=false), then actor is P2.
                // So, this.isHumanPlayer1 correctly identifies if the human is P1.
                handleMoveAction(finalMoveName, actualHumanPokemonList, actualHumanDisplayPanel, this.isHumanPlayer1);
            });
            movesPanel.add(moveButton);
            humanBattleMoveButtons.add(moveButton);
        }
        return movesPanel;
    }

    private JButton createStyledMoveButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180, 220)); // Semi-transparent blue
        button.setOpaque(true); // Needed for custom background on some L&Fs
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createPokeballPanel(List<String> actualHumanPokemonList, JPanel actualHumanDisplayPanel) {
        JPanel pokeballPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pokeballPanel.setOpaque(false);

        humanBattlePokeballButtons.clear();

        for (int i = 0; i < actualHumanPokemonList.size(); i++) {
            String pokemonName = actualHumanPokemonList.get(i);
            int pokemonIndexInList = i;

            JButton pokeballButton = new JButton(); // Create button first
            try {
                // Attempt to load the icon
                ImageIcon icon = new ImageIcon("Poobkemon/mult/pokeball.jpeg"); // Ensure path is correct
                if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
                    System.err.println("Error loading pokeball icon: Poobkemon/mult/pokeball.jpeg");
                    pokeballButton.setText("P"); // Fallback text
                } else {
                    pokeballButton.setIcon(icon);
                }
            } catch (Exception e) {
                System.err.println("Exception loading pokeball icon: " + e.getMessage());
                pokeballButton.setText("P"); // Fallback text
            }
            
            pokeballButton.setPreferredSize(new Dimension(50, 50));
            pokeballButton.setToolTipText("Cambiar a " + pokemonName);

            boolean isThisHumansActualTurn = (this.isPlayer1Turn && this.isHumanPlayer1) || (!this.isPlayer1Turn && !this.isHumanPlayer1);
            pokeballButton.setEnabled(isThisHumansActualTurn);

            pokeballButton.addActionListener(e -> {
                try {
                    // The domain's switchToPokemon expects the index of the pokemon in the coach's list
                    // and it refers to the *current* coach's list.
                    // The 'isHumanPlayer1' flag tells us if the human is coach 0 or 1 in the domain.
                    poobkemon.switchToPokemon(pokemonIndexInList); // Domain handles current player

                    // Update UI for the human player
                    updateBattlePokemonPanel(actualHumanDisplayPanel, pokemonName, this.isHumanPlayer1);
                    // After switching, the turn ends
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
    protected void handleMoveAction(String moveName, List<String> actingPokemonList, JPanel actingPlayerPanel, boolean isActorPlayer1) {
        // 'isActorPlayer1' tells us if Player 1 is acting.
        // We need to know if the HUMAN is acting.
        boolean isHumanActing = (isActorPlayer1 && this.isHumanPlayer1) || (!isActorPlayer1 && !this.isHumanPlayer1);
        String actorDisplayName = isActorPlayer1 ? this.player1Name : this.player2Name;

        if ("N/A".equals(moveName)) {
            if (isHumanActing) { // Only show message if human tried to click N/A
                JOptionPane.showMessageDialog(this, "Movimiento no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        stopTurnTimer(); // Stop timer as action is being processed

        System.out.println("[handleMoveAction] " + actorDisplayName + " (Human? " + isHumanActing + ") uses: " + moveName);

        try {
            // Poobkemon.attack uses the BattleArena's current turn to determine the attacker.
            int damage = poobkemon.attack(moveName, ""); // Second param "" means attack opponent

            // Determine target for UI update
            String targetDisplayName = isActorPlayer1 ? this.player2Name : this.player1Name;
            JPanel targetDisplayPanel = isActorPlayer1 ? this.player2Panel : this.player1Panel;
            // List<String> targetPokemonList = isActorPlayer1 ? this.player2Pokemon : this.player1Pokemon; // For defeat checking

            String message = actorDisplayName + " usó " + moveName + " contra " + targetDisplayName + "!";
            if (damage > 0) {
                message += "\n¡Causó " + damage + " puntos de daño!";
            } else {
                message += "\nEl movimiento no causó daño directo o falló.";
            }
            JOptionPane.showMessageDialog(this, message, "Acción de Batalla", JOptionPane.INFORMATION_MESSAGE);

            // TODO: Update health bars based on actual health from domain model.
            // This requires fetching updated health from poobkemon.getBattleArena().getCoaches()[...].getActivePokemon().getPs()
            // For now, this is a placeholder.
            JProgressBar targetHealthBar = (JProgressBar) targetDisplayPanel.getClientProperty("healthBar");
            if (targetHealthBar != null) {
                // int newHealth = Math.max(0, targetHealthBar.getValue() - damage); // SIMULATED
                // targetHealthBar.setValue(newHealth);
                // if (newHealth == 0) {
                //    handlePokemonDefeated(targetPokemonList, targetDisplayPanel, isActorPlayer1);
                // } else {
                //    switchTurn();
                // }
                // Instead of simulating, fetch real health after attack
                updateAllHealthBars(); // Implement this method to fetch from domain
            }
            
            if (poobkemon.getBattleArena().isBattleFinished()) {
                handleBattleEnd();
            } else {
                switchTurn();
            }

        } catch (PoobkemonException e) {
            JOptionPane.showMessageDialog(this, "Error al usar " + moveName + " por " + actorDisplayName + ": " + e.getMessage(),
                "Error de Batalla", JOptionPane.ERROR_MESSAGE);
            if (isHumanActing) { // If human's action failed, re-enable controls and timer
                startTurnTimer();
                updateBattleButtons();
            } else { // If machine's action failed, just switch turn
                switchTurn();
            }
        }
    }
    
    private void updateAllHealthBars() {
        if (poobkemon == null || poobkemon.getBattleArena() == null || poobkemon.getBattleArena().getCoaches() == null) return;
        domain.Coach[] coaches = poobkemon.getBattleArena().getCoaches();
        if (coaches[0] != null && coaches[0].getActivePokemon() != null && player1Panel != null) {
            updateHealthBarForPanel(player1Panel, coaches[0].getActivePokemon());
        }
        if (coaches[1] != null && coaches[1].getActivePokemon() != null && player2Panel != null) {
            updateHealthBarForPanel(player2Panel, coaches[1].getActivePokemon());
        }
    }

    private void updateHealthBarForPanel(JPanel panel, domain.Pokemon pokemon) {
        JProgressBar healthBar = (JProgressBar) panel.getClientProperty("healthBar");
        if (healthBar != null && pokemon != null) {
            int maxHp = pokemon.getTotalPs();
            int currentHp = pokemon.getPs();
            healthBar.setMaximum(maxHp);
            healthBar.setValue(currentHp);
            healthBar.setString(currentHp + "/" + maxHp);
            if (currentHp <= maxHp * 0.2) healthBar.setForeground(Color.RED);
            else if (currentHp <= maxHp * 0.5) healthBar.setForeground(Color.YELLOW);
            else healthBar.setForeground(Color.GREEN);
        }
    }
    
    private void handleBattleEnd() {
        // Determine winner and show message
        String winnerMessage = "La batalla ha terminado. ";
        // Logic to determine winner based on poobkemon.getBattleArena().getCoaches()
        // For example, check who has fainted Pokémon.
        JOptionPane.showMessageDialog(this, winnerMessage + "Volviendo al menú principal.", "Batalla Terminada", JOptionPane.INFORMATION_MESSAGE);
        gui.returnToMainMenu();
    }


    @Override
    protected void switchTurn() {
        super.switchTurn(); // This calls poobkemon.changeTurn() and toggles isPlayer1Turn

        updateBattleButtons(); // Update button states based on the new turn

        boolean isMachineTurnNow = (this.isPlayer1Turn && !this.isHumanPlayer1) || (!this.isPlayer1Turn && this.isHumanPlayer1);
        if (isMachineTurnNow) {
            Timer delayTimer = new Timer(1500, e -> executeMachineMove());
            delayTimer.setRepeats(false);
            delayTimer.start();
        } else {
            // It's human's turn
            startTurnTimer(); // Restart timer for the human player
        }
    }

    private void executeMachineMove() {
        if (poobkemon.getBattleArena().isBattleFinished()) {
            handleBattleEnd();
            return;
        }

        // Determine which player is the machine
        String machinePlayerName = isHumanPlayer1 ? this.player2Name : this.player1Name;
        List<String> machinePokemonListForUI = isHumanPlayer1 ? this.player2Pokemon : this.player1Pokemon;
        JPanel machineDisplayPanelForUI = isHumanPlayer1 ? this.player2Panel : this.player1Panel;
        boolean isMachineActingAsPlayer1 = !this.isHumanPlayer1; // If human is P2, machine is P1

        // The machine's logic should come from the domain layer (Machine coach)
        // For now, we'll simulate a random move selection based on its available moves.
        String currentMachinePokemonName = (String) machineDisplayPanelForUI.getClientProperty("pokemonName");
        if (currentMachinePokemonName == null && !machinePokemonListForUI.isEmpty()) {
            currentMachinePokemonName = machinePokemonListForUI.get(0);
        }

        // Get moves from the selectedMoves map (populated from PoobkemonGUI)
        List<String> availableMoves = getSelectedMoves(machinePlayerName, currentMachinePokemonName);

        if (availableMoves != null && !availableMoves.isEmpty()) {
            String selectedMove = availableMoves.get(random.nextInt(availableMoves.size()));
            System.out.println("[executeMachineMove] Máquina (" + machinePlayerName + ") va a usar: " + selectedMove);
            // The last argument to handleMoveAction is 'isActorPlayer1'
            handleMoveAction(selectedMove, machinePokemonListForUI, machineDisplayPanelForUI, isMachineActingAsPlayer1);
        } else {
            System.out.println("[executeMachineMove] Máquina (" + machinePlayerName + ") no tiene movimientos. Pasando turno.");
            JOptionPane.showMessageDialog(this,
                machinePlayerName + " no tiene movimientos disponibles y pasa su turno.",
                "Turno de la Máquina", JOptionPane.INFORMATION_MESSAGE);
            switchTurn(); // Machine passes, switch back to human or end.
        }
    }

    @Override
    protected void updateBattleButtons() {
        // This method enables/disables buttons for the HUMAN player only.
        boolean enableHumanControls = (this.isPlayer1Turn && this.isHumanPlayer1) || (!this.isPlayer1Turn && !this.isHumanPlayer1);

        for (JButton btn : humanBattleMoveButtons) {
            // Also check if the move itself is "N/A"
            btn.setEnabled(enableHumanControls && !btn.getText().equals("N/A"));
        }
        for (JButton btn : humanBattlePokeballButtons) {
            btn.setEnabled(enableHumanControls);
        }
    }

    @Override
    protected void onTimerEnd() {
        // This is called when the ShowBattle timer (for human player) ends.
        boolean isHumanTurnCurrently = (this.isPlayer1Turn && this.isHumanPlayer1) || (!this.isPlayer1Turn && !this.isHumanPlayer1);
        if (isHumanTurnCurrently) {
            JOptionPane.showMessageDialog(this,
                "Tiempo agotado. El turno pasa al oponente.",
                "Tiempo Terminado", JOptionPane.WARNING_MESSAGE);
            // The ShowBattle's timer logic should automatically call switchTurn after this.
            // If the domain needs to know about the timeout for the human coach:
            if (poobkemon != null && poobkemon.getBattleArena() != null && poobkemon.getBattleArena().getCoaches() != null) {
                domain.Coach currentDomainCoach = poobkemon.getBattleArena().getCoaches()[poobkemon.getBattleArena().getCurrentTurn()];
                if (currentDomainCoach instanceof domain.HumanCoach) {
                    currentDomainCoach.handleTurnTimeout();
                }
            }
            // switchTurn(); // ShowBattle's timer should handle this. Avoid double switch.
        }
    }
}
