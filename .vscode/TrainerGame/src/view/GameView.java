package view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextArea gameStateArea;
    private JPanel trainerPanel;
    private JButton[] moveButtons;

    public GameView() {
        setTitle("Trainer Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameStateArea = new JTextArea();
        gameStateArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameStateArea);
        add(scrollPane, BorderLayout.CENTER);

        trainerPanel = new JPanel();
        trainerPanel.setLayout(new GridLayout(1, 2));
        add(trainerPanel, BorderLayout.SOUTH);

        moveButtons = new JButton[4]; // Assuming each trainer has 4 moves
        JPanel movePanel = new JPanel();
        movePanel.setLayout(new GridLayout(1, 4));
        for (int i = 0; i < moveButtons.length; i++) {
            moveButtons[i] = new JButton("Move " + (i + 1));
            movePanel.add(moveButtons[i]);
        }
        add(movePanel, BorderLayout.NORTH);
    }

    public void updateGameState(String state) {
        gameStateArea.setText(state);
    }

    public void addTrainerPanel(Component trainerComponent) {
        trainerPanel.add(trainerComponent);
    }

    public void setMoveButtonAction(int index, Action action) {
        if (index >= 0 && index < moveButtons.length) {
            moveButtons[index].setAction(action);
        }
    }
}