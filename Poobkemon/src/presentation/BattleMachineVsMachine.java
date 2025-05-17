package presentation;

import domain.Poobkemon;
import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;
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
        // Obtener la información de la máquina actual
        List<String> currentMachinePokemonList = isPlayer1Turn ? player1Pokemon : player2Pokemon;
        JPanel currentMachinePanel = isPlayer1Turn ? player1Panel : player2Panel;
        String currentMachinePlayer = isPlayer1Turn ? player1Name : player2Name;
        
        // Obtener el Pokémon actual
        String currentPokemon = (String) currentMachinePanel.getClientProperty("pokemonName");
        
        // Obtener los movimientos disponibles
        List<String> availableMoves = getSelectedMoves(currentMachinePlayer, currentPokemon);
        
        if (availableMoves != null && !availableMoves.isEmpty()) {
            // Seleccionar un movimiento aleatorio (en una implementación real,
            // esto debería usar la estrategia de la máquina)
            String selectedMove = availableMoves.get(random.nextInt(availableMoves.size()));
            
            // Mostrar un mensaje con el movimiento seleccionado
            JOptionPane.showMessageDialog(this, 
                currentMachinePlayer + " usa " + selectedMove, 
                "Turno de " + currentMachinePlayer, JOptionPane.INFORMATION_MESSAGE);
            
            // Ejecutar el movimiento
            handleMoveAction(selectedMove, currentMachinePokemonList, currentMachinePanel, isPlayer1Turn);
        } else {
            // Si no hay movimientos disponibles
            JOptionPane.showMessageDialog(this, 
                currentMachinePlayer + " no tiene movimientos disponibles.", 
                "Turno de " + currentMachinePlayer, JOptionPane.INFORMATION_MESSAGE);
            switchTurn();
        }
    }

    @Override
    protected void updateBattleButtons() {
        // No se necesita actualizar botones de ataque aquí
        // Solo informamos del cambio de turno
        JOptionPane.showMessageDialog(this, 
            "Ahora es el turno de " + (isPlayer1Turn ? player1Name : player2Name), 
            "Cambio de Turno", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void onTimerEnd() {
        // En este caso, simplemente cambiamos de turno sin mostrar mensajes,
        // ya que las máquinas no tienen límite de tiempo real
        switchTurn();
    }
    
    @Override
    protected void startTurnTimer() {
        // No utilizamos el temporizador normal para máquina vs máquina
        // En su lugar, nos basamos en el autoplayTimer
    }
    
    @Override
    protected void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateBattleButtons();
        // No reiniciamos el temporizador normal aquí
    }
}