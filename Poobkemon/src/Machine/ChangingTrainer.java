package Machine;
import java.util.List;

public class ChangingTrainer implements Trainer {
    @Override
    public Pokemon elegirPokemon(List<Pokemon> pokemons) {
        // Lógica para elegir el Pokémon con mayor efectividad contra el rival
        return pokemons.get(0); // Implementar lógica específica
    }

    @Override
    public Attack elegirAtaque(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
        // Lógica para elegir el ataque más efectivo contra el rival
        return null; // Implementar lógica específica
    }

    @Override
    public void tomarDecisionEstrategica(BattleArena arena) {
        // Lógica para tomar decisiones estratégicas basadas en el Pokémon rival
    }
}