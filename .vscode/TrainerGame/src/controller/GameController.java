package controller;

import model.Trainer;
import model.TrainerFactory;
import view.GameView;
import view.TrainerSelectionView;

import java.util.Scanner;

public class GameController {
    private Trainer playerTrainer;
    private Trainer opponentTrainer;
    private GameView gameView;
    private TrainerSelectionView trainerSelectionView;

    public GameController() {
        this.gameView = new GameView();
        this.trainerSelectionView = new TrainerSelectionView();
    }

    public void startGame() {
        selectTrainers();
        gameView.displayWelcomeMessage();
        gameLoop();
    }

    private void selectTrainers() {
        String playerType = trainerSelectionView.getTrainerType();
        String opponentType = trainerSelectionView.getOpponentType();

        playerTrainer = TrainerFactory.createTrainer(playerType);
        opponentTrainer = TrainerFactory.createTrainer(opponentType);

        gameView.displayTrainerSelection(playerTrainer, opponentTrainer);
    }

    private void gameLoop() {
        while (!isGameOver()) {
            gameView.displayCurrentState(playerTrainer, opponentTrainer);
            processPlayerTurn();
            if (!isGameOver()) {
                processOpponentTurn();
            }
        }
        gameView.displayGameOverMessage(playerTrainer);
    }

    private void processPlayerTurn() {
        String move = gameView.getPlayerMove(playerTrainer);
        playerTrainer.performMove(move, opponentTrainer);
    }

    private void processOpponentTurn() {
        String move = opponentTrainer.selectMove();
        opponentTrainer.performMove(move, playerTrainer);
    }

    private boolean isGameOver() {
        return playerTrainer.isFainted() || opponentTrainer.isFainted();
    }
}