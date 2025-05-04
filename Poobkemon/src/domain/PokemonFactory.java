package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PokemonFactory {
    // Mapa que asocia nombres de Pokémon con sus constructores
    private static final Map<String, Supplier<Pokemon>> POKEMON_REGISTRY = new HashMap<>();

    // Registramos todos los Pokémon disponibles
    static {
        POKEMON_REGISTRY.put("Blastoise", blastoise::new);
        POKEMON_REGISTRY.put("Charizard", charizard::new);  // Ejemplo adicional
        POKEMON_REGISTRY.put("Pikachu", donphan::new);
        POKEMON_REGISTRY.put("Gengar", gengar::new);
        POKEMON_REGISTRY.put("Dragonite", dragonite::new);
        POKEMON_REGISTRY.put("Togetic", togetic::new);
        POKEMON_REGISTRY.put("Tyranitar", tyranitar::new);
        POKEMON_REGISTRY.put("Gardevoir", gardevoir::new);
        POKEMON_REGISTRY.put("Snorlax", snorlax::new);
        POKEMON_REGISTRY.put("Metagross", metagross::new);
        POKEMON_REGISTRY.put("Machamp", machamp::new);
        POKEMON_REGISTRY.put("Raichu", raichu::new);
        POKEMON_REGISTRY.put("Delibird", delibird::new);
        POKEMON_REGISTRY.put("Venusaur", venusaur::new);
        
    }

    // Método para crear un Pokémon basado en su nombre
    public static Pokemon createPokemon(String name) {
        Supplier<Pokemon> constructor = POKEMON_REGISTRY.get(name);
        if (constructor == null) {
            throw new IllegalArgumentException("Pokémon no reconocido: " + name);
        }
        return constructor.get();
    }
}
