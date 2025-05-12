package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleArenaSurvival extends BattleArena {

    public BattleArenaSurvival() {
        super();
    }

    /**
     * Configura la batalla en modo Survival.
     * Genera Pokémon y movimientos aleatorios para ambos entrenadores.
     */
    public void setupSurvivalBattle(String coachName1, String coachName2) throws PoobkemonException {
        // Generar Pokémon aleatorios
        List<String> player1Pokemon = Poobkemon.getRandomPokemon();
        List<String> player2Pokemon = Poobkemon.getRandomPokemon();

        // Generar movimientos aleatorios para cada Pokémon
        Map<String, List<String>> player1Moves = Poobkemon.getRandomMovesForPokemon(player1Pokemon);
        Map<String, List<String>> player2Moves = Poobkemon.getRandomMovesForPokemon(player2Pokemon);

        // Convertir movimientos a formato de matriz para la batalla
        Attack[][] pokemAttacks1 = convertMovesToAttacks(player1Pokemon, player1Moves);
        Attack[][] pokemAttacks2 = convertMovesToAttacks(player2Pokemon, player2Moves);

        // Configurar entrenadores sin ítems
        setupCoaches(coachName1, coachName2, new ArrayList<>(player1Pokemon), new ArrayList<>(player2Pokemon),
                new ArrayList<>(), new ArrayList<>(), pokemAttacks1, pokemAttacks2);
    }

    /**
     * Convierte los movimientos en formato de lista a una matriz de ataques.
     */
    private Attack[][] convertMovesToAttacks(List<String> pokemons, Map<String, List<String>> moves) {
        Attack[][] attacks = new Attack[pokemons.size()][4];
        for (int i = 0; i < pokemons.size(); i++) {
            String pokemon = pokemons.get(i);
            List<String> pokemonMoves = moves.get(pokemon);
            for (int j = 0; j < 4; j++) {
                attacks[i][j] = AttackFactory.getAttackByName(pokemonMoves.get(j));
            }
        }
        return attacks;
    }
}