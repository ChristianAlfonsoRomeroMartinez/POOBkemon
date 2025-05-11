package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PokemonFactory {
    // Mapa que asocia nombres de Pokémon con sus constructores
    public static final Map<String, Supplier<Pokemon>> POKEMON_REGISTRY = new HashMap<>();

    // Registramos todos los Pokémon disponibles
    static {
        POKEMON_REGISTRY.put("Blastoise", () -> new Pokemon("Blastoise", 2, 362, 280, 295, 291, 339, 328, "Agua",95));
        POKEMON_REGISTRY.put("Charizard", () -> new Pokemon("Charizard", 6, 360, 328, 348, 293, 295, 280, "Fuego", 105));
        POKEMON_REGISTRY.put("Raichu", () -> new Pokemon("Raichu", 14, 324, 350, 306, 306, 284, 229, "Electrico",110));
        POKEMON_REGISTRY.put("Gengar", () -> new Pokemon("Gengar", 4, 324, 350, 394, 251, 273, 240, "Fantasma",115));
        POKEMON_REGISTRY.put("Dragonite", () -> new Pokemon("Dragonite", 5, 386, 284, 328, 403, 328, 317, "Dragon",100));
        POKEMON_REGISTRY.put("Togetic", () -> new Pokemon("Togetic", 6, 314, 196, 284, 196, 339, 295, "Hada",110));
        POKEMON_REGISTRY.put("Tyranitar", () -> new Pokemon("Tyranitar", 7, 404, 243, 317, 403, 328, 350, "Roca",85));
        POKEMON_REGISTRY.put("Gardevoir", () -> new Pokemon("Gardevoir", 8, 340, 284, 383, 251, 361, 251, "Psíquico",105));
        POKEMON_REGISTRY.put("Snorlax", () -> new Pokemon("Snorlax", 9, 524, 174, 251, 350, 350, 251, "Normal",80));
        POKEMON_REGISTRY.put("Metagross", () -> new Pokemon("Metagross", 10, 364, 262, 317, 405, 306, 394, "Acero",90));
        POKEMON_REGISTRY.put("Machamp", () -> new Pokemon("Machamp", 12, 384, 229, 251, 394, 295, 284, "Lucha",100));
        POKEMON_REGISTRY.put("Donphan", () -> new Pokemon("Donphan", 13, 294, 273, 251, 229, 207, 207, "Hielo", 120));
        POKEMON_REGISTRY.put("Delibird", () -> new Pokemon("Delibird", 13, 294, 273, 251, 229, 207, 207, "Hielo", 120));
        POKEMON_REGISTRY.put("Venusaur", () -> new Pokemon("Venusaur", 3, 364, 284, 394, 251, 273, 240, "Planta",90));
        
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
