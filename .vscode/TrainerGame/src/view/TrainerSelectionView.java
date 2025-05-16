package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainerSelectionView extends JFrame {
    private JComboBox<String> trainerTypeComboBox;
    private JButton selectButton;
    private JLabel messageLabel;

    public TrainerSelectionView() {
        setTitle("Select Your Trainer");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        trainerTypeComboBox = new JComboBox<>(new String[]{"Defensive Trainer", "Attacking Trainer"});
        selectButton = new JButton("Select Trainer");
        messageLabel = new JLabel("");

        add(new JLabel("Choose your trainer type:"));
        add(trainerTypeComboBox);
        add(selectButton);
        add(messageLabel);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTrainer = (String) trainerTypeComboBox.getSelectedItem();
                messageLabel.setText("You selected: " + selectedTrainer);
                // Here you would typically notify the controller to start the game
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrainerSelectionView view = new TrainerSelectionView();
            view.setVisible(true);
        });
    }
}