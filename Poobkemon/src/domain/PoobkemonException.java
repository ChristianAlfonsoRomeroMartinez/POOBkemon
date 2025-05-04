package domain;

public class PoobkemonException extends Exception {
    public static final String MAX_CANT_POKEMON = "Un entrenador no puede tener más de 6 Pokémon.";
    public static final String NO_POKEMONS_SELECTED = "No existe ningun pokemon seleccionado";
    public static final String INVALID_POKEMON_INDEX = "Índice de Pokémon inválido";
    public static final String FAINTED_POKEMON = "El pokemon esta debilitado";
    public static final String POKEMON_HAS_BEEN_FAINTED = "El pokemon ha sido debilitado";
    public static final String POKEMON_GAME_OVER = "El juego ha terminado, no hay más pokemones disponibles";


    
    public PoobkemonException(String message) {
        super(message);
    }
}