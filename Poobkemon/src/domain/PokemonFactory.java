package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PokemonFactory {
    // Mapa que asocia nombres de Pokémon con sus constructores
    private static final Map<String, Supplier<Pokemon>> POKEMON_REGISTRY = new HashMap<>();

    // Registramos todos los Pokémon disponibles
    static {
        POKEMON_REGISTRY.put("Blastoise", Blastoise::new);
        POKEMON_REGISTRY.put("Charizard", Charizard::new);  // Ejemplo adicional
        POKEMON_REGISTRY.put("Pikachu", Donphan::new);
        POKEMON_REGISTRY.put("Gengar", Gengar::new);
        POKEMON_REGISTRY.put("Dragonite", Dragonite::new);
        POKEMON_REGISTRY.put("Togetic", Togetic::new);
        POKEMON_REGISTRY.put("Tyranitar", Tyranitar::new);
        POKEMON_REGISTRY.put("Gardevoir", Gardevoir::new);
        POKEMON_REGISTRY.put("Snorlax", Snorlax::new);
        POKEMON_REGISTRY.put("Metagross", Metagross::new);
        POKEMON_REGISTRY.put("Machamp", Machamp::new);
        POKEMON_REGISTRY.put("Raichu", Raichu::new);
        POKEMON_REGISTRY.put("Delibird", Delibird::new);
        POKEMON_REGISTRY.put("Venusaur", Venusaur::new);
        
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
