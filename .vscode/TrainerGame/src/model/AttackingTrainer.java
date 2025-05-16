package model;

import java.util.List;

public class AttackingTrainer extends Trainer {
    
    public AttackingTrainer(String name) {
        super(name, "Attacking");
    }

    @Override
    public void selectMove(List<String> availableMoves) {
        // Implement logic to select an offensive move
        // For example, prioritize moves that increase attack or decrease opponent's defense
        String selectedMove = availableMoves.stream()
            .filter(move -> isOffensiveMove(move))
            .findFirst()
            .orElse(availableMoves.get(0)); // Default to the first available move if none found
        setCurrentMove(selectedMove);
    }

    private boolean isOffensiveMove(String move) {
        // Logic to determine if the move is offensive
        // This could be based on move type, damage, etc.
        return move.contains("Attack") || move.contains("Hit");
    }
}