package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Trainer {
    protected String name;
    protected String type;
    protected List<Pokemon> pokemonTeam;
    protected Pokemon activePokemon;
    protected int activePokemonIndex;

    public Trainer(String name, String type) {
        this.name = name;
        this.type = type;
        this.pokemonTeam = new ArrayList<>();
        this.activePokemonIndex = 0;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public void setPokemonTeam(List<Pokemon> pokemonTeam) {
        this.pokemonTeam = new ArrayList<>(pokemonTeam);
        if (!pokemonTeam.isEmpty()) {
            this.activePokemon = pokemonTeam.get(0);
        }
    }
    
    public Pokemon getActivePokemon() {
        return activePokemon;
    }
    
    public void switchPokemon(int index) {
        if (index >= 0 && index < pokemonTeam.size()) {
            activePokemonIndex = index;
            activePokemon = pokemonTeam.get(activePokemonIndex);
        }
    }
    
    public boolean isAlive() {
        for (Pokemon pokemon : pokemonTeam) {
            if (pokemon.getPs() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Selecciona un movimiento basado en la estrategia del entrenador
     * @return El nombre del movimiento seleccionado
     */
    public abstract String selectMove();
    
    /**
     * Evalúa los pokémon del equipo y selecciona el mejor para la batalla
     * según la estrategia del entrenador.
     * @return El índice del pokémon seleccionado
     */
    public abstract int selectBestPokemon();
}