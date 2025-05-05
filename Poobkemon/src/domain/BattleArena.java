package domain;

import java.util.ArrayList;
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
    private long timeRemaining = MAX_TIME_SECONDS * 1000L; // Tiempo restante en milisegundos
    private long pauseStartTime; // Momento en que se pausó el juego

    public BattleArena() {
        this.isPaused = false;
    }

    /**
     * Inicia una batalla Player vs Player.
     */
    public void startBattle(String coachName1, String coachName2, ArrayList<String> pokemons1
    , ArrayList<String> pokemons2, ArrayList<String> items1, ArrayList<String> items2,
    Attack[][] pokemAttacks1,Attack[][] pokemAttacks2) throws PoobkemonException {
        
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
    protected void setupCoaches(String coachName1, String coachName2, ArrayList<String> pokemons1
    , ArrayList<String> pokemons2, ArrayList<String> items1, ArrayList<String> items2,
    Attack[][] pokemAttacks1,Attack[][] pokemAttacks2 ) throws PoobkemonException {
        boolean firstStarts = rand.nextBoolean();
        if (firstStarts) {
            coaches[0] = new HumanCoach(coachName1, pokemons1, items1);
            coaches[0].setPokemonAttacks(pokemAttacks1);

            coaches[1] = new HumanCoach(coachName2, pokemons2, items2);
            coaches[1].setPokemonAttacks(pokemAttacks2);
        } else {
            coaches[0] = new HumanCoach(coachName2, pokemons2, items2);
            coaches[0].setPokemonAttacks(pokemAttacks2);

            coaches[1] = new HumanCoach(coachName1, pokemons1, items1);
            coaches[1].setPokemonAttacks(pokemAttacks1);

        }
        currentTurn = 0;
    }

    /**
     * Verifica si la batalla ha terminado.
     */
    public boolean isBattleFinished() {
    	System.out.println(coaches[0].areAllPokemonFainted() || coaches[1].areAllPokemonFainted());
        return coaches[0].areAllPokemonFainted() || coaches[1].areAllPokemonFainted();
    }

    /**
     * Inicia el temporizador para un turno específico.
     */
    protected void startTurnTimer(final int coachIndex) {
        turnTimer = new Timer();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused) {
                    //System.out.println("Tiempo excedido para " + coaches[coachIndex].getName());
                    coaches[coachIndex].handleTurnTimeout();
                    cancelTurnTimer();
                    currentTurn = 1 - coachIndex; // Cambiar turno automáticamente
                }
            }
        }, timeRemaining);
    }

    /**
     * Cancela el temporizador de turno activo.
     */
    protected void cancelTurnTimer() {
        if (turnTimer != null) {
            turnTimer.cancel();
            turnTimer = null;
        }
    }

    /**
     * Anuncia el ganador de la batalla.
     */
    protected void announceWinner() {
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
        if (!isPaused) {
            this.isPaused = true;
            pauseStartTime = System.currentTimeMillis(); // Guardar el momento en que se pausó
            cancelTurnTimer(); // Detener el temporizador
            System.out.println("La batalla ha sido pausada.");
        }
    }

    /**
     * Reanuda la batalla.
     */
    public void resumeBattle() {
        if (isPaused) {
            this.isPaused = false;
            long pauseDuration = System.currentTimeMillis() - pauseStartTime; // Calcular la duración de la pausa
            timeRemaining -= pauseDuration; // Reducir el tiempo restante por la duración de la pausa
            System.out.println("La batalla ha sido reanudada. Tiempo restante: " + timeRemaining / 1000 + " segundos.");
            startTurnTimer(currentTurn); // Reiniciar el temporizador con el tiempo restante
        }
    }

    /**
     * Finaliza la batalla y limpia recursos.
     */
    public void endBattle() {
        cancelTurnTimer();
        System.out.println("La batalla ha terminado.");
    }

    /**
     * Obtiene el entrenador actual.
     */
    protected Coach getCurrentCoach() {
        return coaches[currentTurn];
    }

    /**
     * Obtiene el entrenador oponente.
     */
    protected Coach getOpponentCoach() {
        return coaches[1 - currentTurn];
    }

    /**
     * Cambia al siguiente turno.
     */
    protected void nextTurn() {
        currentTurn = 1 - currentTurn;
    }

    /**
     * Obtiene el turno actual.
     */
    protected int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Obtiene los entrenadores.
     */
    protected Coach[] getCoaches() {
        return coaches;
    }
}
