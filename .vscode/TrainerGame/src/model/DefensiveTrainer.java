package model;

import java.util.List;

public class DefensiveTrainer extends Trainer {

    public DefensiveTrainer(String name) {
        super(name, "Defensive");
    }

    @Override
    public String selectMove(List<String> availableMoves) {
        // Implement logic to select a defensive move
        // For example, prioritize moves that enhance defense or reduce opponent's attack
        for (String move : availableMoves) {
            if (isDefensiveMove(move)) {
                return move;
            }
        }
        // Fallback to a default move if no defensive move is found
        return availableMoves.get(0);
    }

    private boolean isDefensiveMove(String move) {
        // Logic to determine if the move is defensive
        // This could be based on move names, types, or effects
        return move.contains("Shield") || move.contains("Guard");
    }

    @Override
    public void applyEffects() {
        // Implement logic to apply defensive effects during battles
        // For example, increase defense stats or heal
    }
}