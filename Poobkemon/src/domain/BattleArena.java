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
     * Configura los entrenadores y determina quién inicia.
     */
    public void setupCoaches(String coachName1, String coachName2, ArrayList<String> pokemons1,
                             ArrayList<String> pokemons2, ArrayList<String> items1, ArrayList<String> items2,
                             String[][] pokemAttacks1, String[][] pokemAttacks2) throws PoobkemonException {
        boolean firstStarts = rand.nextBoolean();

        // Crear y asignar entrenadores
        if (firstStarts) {
            coaches[0] = new HumanCoach(coachName1, createPokemonList(pokemons1, pokemAttacks1), items1);
            coaches[1] = new HumanCoach(coachName2, createPokemonList(pokemons2, pokemAttacks2), items2);
        } else {
            coaches[0] = new HumanCoach(coachName2, createPokemonList(pokemons2, pokemAttacks2), items2);
            coaches[1] = new HumanCoach(coachName1, createPokemonList(pokemons1, pokemAttacks1), items1);
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
    public Coach[] getCoaches() {
        return coaches;
    }

    public void attack(String moveName, String itself) throws PoobkemonException {
        Coach currentCoach = getCurrentCoach();
        Coach opponentCoach = getOpponentCoach();

        Pokemon attacker = currentCoach.getActivePokemon();
        Pokemon defender = opponentCoach.getActivePokemon();

        // Encuentra el ataque por nombre
        Attack attack = attacker.getAtaques().stream()
            .filter(a -> a.getName().equals(moveName))
            .findFirst()
            .orElse(null); // Si no encuentra el ataque, devuelve null

        if (attack.getPowerPoint() > 0){
            if ("itself".equals(itself)) {
            // Realiza el ataque sobre sí mismo
            
            attacker.attack(attacker, attack);
            // Cambia el turno al siguiente entrenador
            nextTurn();
            } else {
            // Realiza el ataque sobre el oponente
            attacker.attack(defender, attack);
            }
        } else {
            System.out.println("No puedes usar este ataque, no tienes PP.");
        }
    }

    public void flee() {
        getCurrentCoach().fleeBattle(); // Marca al entrenador actual como que ha huido
        endBattle(); // Finaliza la batalla
    }

    public void useItem(String itemName) throws PoobkemonException {
        // Obtener el entrenador actual
        Coach currentCoach = getCurrentCoach();

        // Buscar el ítem por su nombre
        Item item = ItemFactory.createItem(itemName);

        // Delegar el uso del ítem al entrenador actual
        currentCoach.useItem(item);
    }

    public void switchToPokemon(int index) throws PoobkemonException {
        // Obtener el entrenador actual
        Coach currentCoach = getCurrentCoach();

        // Cambiar al Pokémon activo
        currentCoach.switchPokemon(index);
    }

    private ArrayList<Pokemon> createPokemonList(ArrayList<String> pokemonNames, String[][] pokemAttacks) {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            String pokemonName = pokemonNames.get(i);

            // Crear el Pokémon
            Pokemon pokemon = PokemonFactory.createPokemon(pokemonName);

            // Asignar ataques al Pokémon
            for (String attackName : pokemAttacks[i]) {
                if (attackName != null) {
                    Attack attack = AttackFactory.createAttack(attackName);
                    pokemon.addAttack(attack);
                }
            }

            // Agregar el Pokémon a la lista
            pokemonList.add(pokemon);
        }

        return pokemonList;
    }
}
