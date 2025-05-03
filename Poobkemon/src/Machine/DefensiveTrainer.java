
package machine;

import domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DefensiveTrainer implements Trainer {
    @Override
    public Pokemon elegirPokemon(List<Pokemon> pokemons) {
        // Lógica para elegir un Pokémon defensivo
        return pokemons.get(new Random().nextInt(pokemons.size())); // Implementar lógica específica
    }

    @Override
    public Attack elegirAtaque(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
         List<Attack> ataques = getAtaquesDefensivos(pokemonAtacante);
        if (!ataques.isEmpty()) {
            return ataques.get(new Random().nextInt(ataques.size()));
        } else {
            // Si no hay ataques defensivos, elige uno al azar
            return elegirAtaqueAleatorio(pokemonAtacante, pokemonDefensor);
        }
    }

    private List<Attack> getAtaquesDefensivos(Pokemon pokemon) {
        List<Attack> ataquesDefensivos = new ArrayList<>();
        // Agregar ataques de estado que aumentan la defensa o la defensa especial
        for (Attack ataque : pokemon.getAtaques()) {
            if (ataque instanceof StatusAttack) {
                StatusAttack statusAttack = (StatusAttack) ataque;
                if (statusAttack.aumentaDefensa() || statusAttack.aumentaDefensaEspecial()) {
                    ataquesDefensivos.add(ataque);
                }
            }
        }
        return ataquesDefensivos;
    }

    private Attack elegirAtaqueAleatorio(Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
        List<Attack> ataques = pokemonAtacante.getAtaques();
        return ataques.get(new Random().nextInt(ataques.size()));
    }

    @Override
    public void tomarDecisionEstrategica(BattleArena arena) {
        // Lógica para tomar decisiones estratégicas defensivas
    }
}