package Machine;

import java.util.List;

public class ExpertTrainer implements Trainer {
    @Override
    public Pokemon elegirPokemon(List<Pokemon> pokemons) {
        // Lógica para elegir el mejor Pokémon
        return pokemons.get(0); // Implementar lógica específica
    }

    @Override
    public Attack elegirAtaque(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
        // Lógica para elegir el mejor ataque
        return null; // Implementar lógica específica
    }

    @Override
    public void tomarDecisionEstrategica(BattleArena arena) {
        // Lógica para tomar las mejores decisiones estratégicas
    }
}