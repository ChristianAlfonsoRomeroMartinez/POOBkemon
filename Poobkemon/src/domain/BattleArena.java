package domain;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BattleArena {
    private int currentTurn = 0;
    private static final int MAX_TIME_SECONDS = 20;
    private Coach[] coaches = new Coach[2];
    private boolean isPaused;
    private Timer turnTimer;
    private Random rand = new Random();

    public BattleArena(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Inicia una batalla Player vs Player.
     */
    public void startBattlePvP(String coachName1, String coachName2) throws PoobkemonException {
        setupCoaches(coachName1, coachName2);

        while (!isBattleFinished()) {
            try {
                // Iniciar turno
                //System.out.println("Turno de: " + coaches[currentTurn].getName());
                startTurnTimer(currentTurn);

                // Ejecuta la acción del entrenador actual
                coachAction(currentTurn);

                // Detener el temporizador si la acción se completó a tiempo
                cancelTurnTimer();

                // Verificar si algún Pokémon murió
                checkPokemonStatus();

                // Cambiar de turno
                currentTurn = 1 - currentTurn;

            } catch (PoobkemonException e) {
                System.out.println("Error durante la batalla: " + e.getMessage());
                break;
            }
        }

        // Finalizar batalla y determinar ganador
        announceWinner();
        endBattle();
    }

    /**
     * Ejecuta la acción del entrenador en el índice dado.
     */
    private void coachAction(int index) throws PoobkemonException {
        // Obtiene la acción (ataque, cambio, ítem o huir)
        int action = coaches[index].selectAction();
        // Realiza la acción
        coaches[index].doAction(action);
    }

    /**
     * Configura los entrenadores y determina quién inicia.
     */
    private void setupCoaches(String coachName1, String coachName2) {
        boolean firstStarts = rand.nextBoolean();
        if (firstStarts) {
            coaches[0] = new HumanCoach(coachName1);
            coaches[1] = new HumanCoach(coachName2);
        } else {
            coaches[0] = new HumanCoach(coachName2);
            coaches[1] = new HumanCoach(coachName1);
        }
        currentTurn = 0;
    }

    /**
     * Verifica si la batalla ha terminado.
     */
    public boolean isBattleFinished() {
        return coaches[0].areAllPokemonFainted() || coaches[1].areAllPokemonFainted();
    }

    /**
     * Inicia el temporizador para un turno específico.
     */
    private void startTurnTimer(final int coachIndex) {
        turnTimer = new Timer();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Tiempo excedido para " + coaches[coachIndex].getName());
                coaches[coachIndex].handleTurnTimeout();
                cancelTurnTimer();
                currentTurn = 1 - coachIndex; // Cambiar turno automáticamente
            }
        }, MAX_TIME_SECONDS * 1000L);
    }

    /**
     * Cancela el temporizador de turno activo.
     */
    private void cancelTurnTimer() {
        if (turnTimer != null) {
            turnTimer.cancel();
            turnTimer = null;
        }
    }

    /**
     * Verifica el estado de los Pokémon después de cada acción.
     */
    private void checkPokemonStatus() throws PoobkemonException {
        if (coaches[0].getActivePokemon().getPs() == 0) {
            //System.out.println(coaches[0].getName() + " perdió un Pokémon.");
            if (coaches[0].areAllPokemonFainted()) {
                throw new PoobkemonException(PoobkemonException.POKEMON_HAS_BEEN_FAINTED);
            }
        }
        if (coaches[1].getActivePokemon().getPs() == 0) {
            //System.out.println(coaches[1].getName() + " perdió un Pokémon.");
            if (coaches[1].areAllPokemonFainted()) {
                throw new PoobkemonException(PoobkemonException.POKEMON_HAS_BEEN_FAINTED);
            }
        }
    }

    /**
     * Anuncia el ganador de la batalla.
     */
    private void announceWinner() {
        if (coaches[0].areAllPokemonFainted()) {
            //System.out.println("Ganador: " + coaches[1].getName());
        } else if (coaches[1].areAllPokemonFainted()) {
            //System.out.println("Ganador: " + coaches[0].getName());
        } else {
            System.out.println("La batalla terminó en empate.");
        }
    }

    /**
     * Pausa la batalla.
     */
    public void pauseBattle() {
        this.isPaused = true;
        cancelTurnTimer();
        System.out.println("La batalla ha sido pausada.");
    }

    /**
     * Reanuda la batalla.
     */
    public void resumeBattle() {
        this.isPaused = false;
        System.out.println("La batalla ha sido reanudada.");
        startTurnTimer(currentTurn);
    }

    /**
     * Finaliza la batalla y limpia recursos.
     */
    public void endBattle() {
        cancelTurnTimer();
        System.out.println("La batalla ha terminado.");
    }
}
