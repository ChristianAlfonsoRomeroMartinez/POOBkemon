package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poobkemon {
    private BattleArenaNormal battleArenaNormal;
    private ArrayList<BattleArena> battleArenas;
    private BattleArenaSurvival battleArenaSurvival;

    //Metodo que envia informacion de los pokemones disponibles a la GUI
    public static List<String> getAvailablePokemon() {
        return Arrays.asList(PokemonFactory.POKEMON_REGISTRY.keySet().toArray(new String[0]));
    }
    
    // Metodo que envia informacion de los items disponibles a la GUI
    public static List<String> getAvailableItems() {
        // Obtiene las claves del mapa ITEM_REGISTRY de ItemFactory
        return new ArrayList<>(ItemFactory.getItemNames());
    }

    /**
     * Obtiene la lista de ataques físicos disponibles.
     */
    public static List<String> getPhysicalAttacks() {
        return PhysicalAttack.ataquesFisicos.stream().map(Attack::getName).toList();
    }

    /**
     * Obtiene la lista de ataques especiales disponibles.
     */
    public static List<String> getSpecialAttacks() {
        return SpecialAttack.ataquesEspeciales.stream().map(Attack::getName).toList();
    }

    /**
     * Obtiene la lista de ataques de estado disponibles.
     */
    public static List<String> getStatusAttacks() {
        return StatusAttack.ataquesStatus.stream().map(Attack::getName).toList();
    }

    /**
     * Genera una lista de 6 Pokémon aleatorios.
     */
    public static List<String> getRandomPokemon() {
        List<String> availablePokemon = getAvailablePokemon();
        Collections.shuffle(availablePokemon); // Mezcla aleatoriamente la lista
        return availablePokemon.subList(0, 6); // Selecciona los primeros 6 Pokémon
    }

    /**
     * Genera un mapa con 4 movimientos aleatorios para cada Pokémon.
     */
    public static Map<String, List<String>> getRandomMovesForPokemon(List<String> pokemons) {
        Map<String, List<String>> pokemonMoves = new HashMap<>();
        List<String> allMoves = new ArrayList<>();
        allMoves.addAll(getPhysicalAttacks());
        allMoves.addAll(getSpecialAttacks());
        allMoves.addAll(getStatusAttacks());

        for (String pokemon : pokemons) {
            Collections.shuffle(allMoves); // Mezcla aleatoriamente los movimientos
            pokemonMoves.put(pokemon, allMoves.subList(0, 4)); // Asigna 4 movimientos al Pokémon
        }

        return pokemonMoves;
    }

    public void attack(String moveName, String itself) throws PoobkemonException {
        // Delegar la lógica del ataque a la arena de batalla
        battleArenaNormal.attack(moveName, itself);

    }

    /**
     * Inicia una nueva batalla entre dos entrenadores.
     * @param coachName1 Nombre del primer entrenador.
     * @param coachName2 Nombre del segundo entrenador.
     * @throws PoobkemonException Si ocurre un error al configurar la batalla.
     */
    public void startBattleNormal(String coachName1, String coachName2, ArrayList<String> pokemons1,
                            ArrayList<String> pokemons2, ArrayList<String> items1, ArrayList<String> items2,
                            Attack[][] pokemAttacks1, Attack[][] pokemAttacks2) throws PoobkemonException {
        battleArenaNormal = new BattleArenaNormal(); // Crear la arena de batalla
        battleArenaNormal.setupCoaches(coachName1, coachName2, pokemons1, pokemons2, items1, items2, pokemAttacks1, pokemAttacks2);
    }

    /**
     * Inicia una nueva batalla en modo Survival entre dos entrenadores.
     * @param coachName1 Nombre del primer entrenador.
     * @param coachName2 Nombre del segundo entrenador.
     * @throws PoobkemonException Si ocurre un error al configurar la batalla.
     */
    public void startSurvivalBattle(String coachName1, String coachName2) throws PoobkemonException {
        battleArenaSurvival = new BattleArenaSurvival();
        battleArenaSurvival.setupSurvivalBattle(coachName1, coachName2);
    }

    public void flee(){
        battleArenaNormal.flee();
    }
    
    public void useItem(String itemName) throws PoobkemonException {
        // Delegar la lógica del uso de ítem a la arena de batalla
        battleArenaNormal.useItem(itemName);
    }

    public void switchToPokemon(int index) throws PoobkemonException {
        // Delegar la lógica del cambio de Pokémon a la arena de batalla
        battleArenaNormal.switchToPokemon(index);
    }

}


