package Machine;


import java.util.List;
import java.util.Random;

public class AttackingTrainer implements Trainer {
    @Override
    public Pokemon elegirPokemon(List<Pokemon> pokemons) {
        // Lógica para elegir un Pokémon ofensivo
        return pokemons.get(new Random().nextInt(pokemons.size())); // Implementar lógica específica
    }

   @Override
    public Attack elegirAtaque(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
        List<Attack> ataques = getAtaquesOfensivos(pokemonAtacante);
        if (!ataques.isEmpty()) {
            return ataques.get(new Random().nextInt(ataques.size()));
        } else {
            // Si no hay ataques ofensivos, elige uno al azar
            return elegirAtaqueAleatorio(pokemonAtacante, pokemonDefensor);
        }
    }

    private List<Attack> getAtaquesOfensivos(Pokemon pokemon) {
        List<Attack> ataquesOfensivos = new ArrayList<>();
        // Agregar ataques especiales y físicos
        for (Attack ataque : pokemon.getAtaques()) {
            if (ataque instanceof SpecialAttack || ataque instanceof PhysicalAttack) {
                ataquesOfensivos.add(ataque);
            }
        }
        return ataquesOfensivos;
    }

    private Attack elegirAtaqueAleatorio(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
        List<Attack> ataques = pokemonAtacante.getAtaques();
        return ataques.get(new Random().nextInt(ataques.size()));
    }

    @Override
    public void tomarDecisionEstrategica(BattleArena arena) {
        // Lógica para tomar decisiones estratégicas ofensivas
    }
}