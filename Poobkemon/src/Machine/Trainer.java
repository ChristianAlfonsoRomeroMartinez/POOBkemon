package Machine;

import java.util.List;

public interface Trainer {
    Pokemon elegirPokemon(List<Pokemon> pokemons);
    Attack elegirAtaque(Pokemon pokemonAtacante, Pokemon pokemonDefensor);
    void tomarDecisionEstrategica(BattleArena arena);
}