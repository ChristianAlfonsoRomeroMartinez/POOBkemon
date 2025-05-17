package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleHumanVsHuman extends ShowBattle {
    // Botones de la interfaz
    private List<JButton> player1BattleMoveButtons = new ArrayList<>();
    private List<JButton> player2BattleMoveButtons = new ArrayList<>();
    private List<JButton> player1BattlePokeballButtons = new ArrayList<>();
    private List<JButton> player2BattlePokeballButtons = new ArrayList<>();

    public BattleHumanVsHuman(List<String> player1Pokemon, List<String> player2Pokemon, 
                             String player1Name, String player2Name, 
                             Poobkemon poobkemon, PoobkemonGUI gui) {
        super(player1Pokemon, player2Pokemon, player1Name, player2Name, poobkemon, gui);
    }

    @Override
    protected JPanel createActionPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);

        // Panel de movimientos y Pokébolas para el jugador 1
        JPanel player1Actions = new JPanel(new BorderLayout());
        player1Actions.setOpaque(false);
        player1Actions.add(createMovesPanel(player1Pokemon, player1Panel, true), BorderLayout.CENTER);
        player1Actions.add(createPokeballPanel(player1Pokemon, player1Panel, true), BorderLayout.SOUTH);

        // Panel de movimientos y Pokébolas para el jugador 2
        JPanel player2Actions = new JPanel(new BorderLayout());
        player2Actions.setOpaque(false);
        player2Actions.add(createMovesPanel(player2Pokemon, player2Panel, false), BorderLayout.CENTER);
        player2Actions.add(createPokeballPanel(player2Pokemon, player2Panel, false), BorderLayout.SOUTH);

        panel.add(player1Actions);
        panel.add(player2Actions);

        updateBattleButtons();
        return panel;
    }

    private JPanel createMovesPanel(List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
        JPanel movesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movesPanel.setOpaque(false);

        List<JButton> moveButtons = isPlayer1 ? player1BattleMoveButtons : player2BattleMoveButtons;
        moveButtons.clear();

        String currentPlayer = isPlayer1 ? player1Name : player2Name;
        String currentPokemon = (String) playerPanel.getClientProperty("pokemonName");
        List<String> moves = getSelectedMoves(currentPlayer, currentPokemon);

        for (int i = 0; i < 4; i++) {
            String moveName = (moves != null && i < moves.size()) ? moves.get(i) : "N/A";
            JButton moveButton = createMoveButton(moveName);
            moveButton.setEnabled(isPlayer1Turn == isPlayer1 && !"N/A".equals(moveName));
            moveButton.addActionListener(e -> handleMoveAction(moveName, pokemonList, playerPanel, isPlayer1));
            movesPanel.add(moveButton);
            moveButtons.add(moveButton);
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

    private JPanel createPokeballPanel(List<String> pokemonList, JPanel playerPanel, boolean isPlayer1) {
        JPanel pokeballPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pokeballPanel.setOpaque(false);

        List<JButton> pokeballButtons = isPlayer1 ? player1BattlePokeballButtons : player2BattlePokeballButtons;
        pokeballButtons.clear();

        for (int i = 0; i < pokemonList.size(); i++) {
            String pokemonName = pokemonList.get(i);
            int index = i; // Capturar el índice para usarlo en el ActionListener
            
            JButton pokeballButton = new JButton(new ImageIcon("Poobkemon/mult/pokeball.jpeg"));
            pokeballButton.setPreferredSize(new Dimension(50, 50));
            pokeballButton.setToolTipText(pokemonName);
            pokeballButton.setEnabled(isPlayer1Turn == isPlayer1);
            pokeballButton.addActionListener(e -> {
                try {
                    // Cambiar Pokémon en el dominio
                    poobkemon.switchToPokemon(index);
                    
                    // Actualizar en la UI
                    updateBattlePokemonPanel(playerPanel, pokemonName, isPlayer1);
                    switchTurn();
                } catch (PoobkemonException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error al cambiar de Pokémon: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            pokeballPanel.add(pokeballButton);
            pokeballButtons.add(pokeballButton);
        }

        return pokeballPanel;
    }

    @Override
    protected void updateBattleButtons() {
        // Activar/desactivar botones según el turno
        for (JButton btn : player1BattleMoveButtons) {
            btn.setEnabled(isPlayer1Turn);
        }
        for (JButton btn : player2BattleMoveButtons) {
            btn.setEnabled(!isPlayer1Turn);
        }
        for (JButton btn : player1BattlePokeballButtons) {
            btn.setEnabled(isPlayer1Turn);
        }
        for (JButton btn : player2BattlePokeballButtons) {
            btn.setEnabled(!isPlayer1Turn);
        }
    }

    @Override
    protected void onTimerEnd() {
        JOptionPane.showMessageDialog(this, 
            "Tiempo agotado. Turno del siguiente jugador.", 
            "Turno", JOptionPane.WARNING_MESSAGE);
        switchTurn();
    }
}