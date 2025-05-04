package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Coach {
    public static final int MAX_CANT_POKEMON = 6;
    //private String name; // Nombre del entrenador
    private List<Pokemon> pokemons; // Lista de Pokémon del entrenador
    private int activePokemonIndex; // Índice del Pokémon actualmente en batalla
    private List<Item> items; // Lista de objetos del entrenador
    private int score;

    public Coach() {
        this.pokemons = new ArrayList<>();
        this.activePokemonIndex = 0; // Por defecto, el primer Pokémon es el activo
        this.score = 0;
    }

    public void addPokemon(Pokemon pokemon) throws PoobkemonException{
        if (pokemons.size() >= MAX_CANT_POKEMON) {
            throw new PoobkemonException(PoobkemonException.MAX_CANT_POKEMON);
        }
        pokemons.add(pokemon);
    }

    public void deletePokemon(Pokemon pokemon){
        pokemons.remove(pokemon);
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    

    public Pokemon getActivePokemon() {
        if (pokemons.isEmpty()) {
            return null;
        }
        return pokemons.get(activePokemonIndex);
    }

    /**
     * Cambia al Pokémon activo al indicado por el jugador.
     * @param index Índice del Pokémon al que se desea cambiar.
     * @throws PoobkemonException si el índice es inválido o el Pokémon está debilitado.
     */
    public void switchToPokemon(int index) throws PoobkemonException {
        if (index < 0 || index >= pokemons.size()) {
            throw new PoobkemonException(PoobkemonException.INVALID_POKEMON_INDEX);
        }
        Pokemon selected = pokemons.get(index);
        if (selected.getPs() <= 0) {
            throw new PoobkemonException(PoobkemonException.FAINTED_POKEMON);
        }
        this.activePokemonIndex = index;
    }


    public boolean areAllPokemonFainted() {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getPs() > 0) {
                return false;
            }
        }
        return true;
    }

    public void useItem(Item item) {
        Pokemon activePokemon = getActivePokemon();
        if (activePokemon != null) {
            item.applyItemEffect(activePokemon);
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Ejecuta la acción del turno del entrenador.
     * Este método debe ser implementado por las subclases (HumanCoach o MachineCoach).
     * @param actionIndex el índice de la acción seleccionada (por ejemplo: 0=ataque, 1=cambiar, etc.)
     */
    public abstract void doAction(int actionIndex) throws PoobkemonException;

    /**
     * Maneja el tiempo agotado del turno.
     * Puedes penalizar al entrenador (por ejemplo, perder PP o cambiar de pokémon).
     */
    public abstract void handleTurnTimeout();

    /**
     * Devuelve el pokemon actual del entrenador
     */
    public Pokemon getCurrentPokemons() {
        return pokemons.get(activePokemonIndex);
    }
}
