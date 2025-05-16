package domain;

import java.util.List;

public class GameLogic {
    private Trainer trainer1;
    private Trainer trainer2;

    public GameLogic(Trainer trainer1, Trainer trainer2) {
        this.trainer1 = trainer1;
        this.trainer2 = trainer2;
    }

    public void startBattle() {
        while (trainer1.isAlive() && trainer2.isAlive()) {
            processTurn(trainer1, trainer2);
            if (trainer2.isAlive()) {
                processTurn(trainer2, trainer1);
            }
        }
        declareWinner();
    }

    private void processTurn(Trainer attacker, Trainer defender) {
        Move move = attacker.selectMove();
        defender.applyMove(move);
        System.out.println(attacker.getName() + " uses " + move.getName() + " on " + defender.getName());
    }

    private void declareWinner() {
        if (trainer1.isAlive()) {
            System.out.println(trainer1.getName() + " wins!");
        } else {
            System.out.println(trainer2.getName() + " wins!");
        }
    }
}