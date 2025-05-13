package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Poobkemon {
    private BattleArena battleArenaNormal;
    private ArrayList<BattleArena> battleArenas;

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
        return AttackFactory.getAllAttacks().values().stream()
            .filter(attack -> "Physical".equals(attack.getAttackType()))
            .map(Attack::getName)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de ataques especiales disponibles.
     */
    public static List<String> getSpecialAttacks() {
        return AttackFactory.getAllAttacks().values().stream()
            .filter(attack -> "Special".equals(attack.getAttackType()))
            .map(Attack::getName)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de ataques de estado disponibles.
     */
    public static List<String> getStatusAttacks() {
        return AttackFactory.getAllAttacks().values().stream()
            .filter(attack -> "Status".equals(attack.getAttackType()))
            .map(Attack::getName)
            .collect(Collectors.toList());
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
                            String[][] pokemAttacks1, String[][] pokemAttacks2) throws PoobkemonException {
        battleArenaNormal = new BattleArenaNormal(); // Crear la arena de batalla
        battleArenaNormal.setupCoaches(coachName1, coachName2, pokemons1, pokemons2, items1, items2, pokemAttacks1, pokemAttacks2);
    }

    /**
     * Inicia una batalla en modo supervivencia con Pokémon aleatorios.
     * @param coachName1 Nombre del primer entrenador
     * @param coachName2 Nombre del segundo entrenador
     * @throws PoobkemonException Si ocurre un error al configurar la batalla
     */
    public void startBattleSurvival(String coachName1, String coachName2) throws PoobkemonException {
        // Crear la arena de batalla para modo supervivencia
        BattleArenaSurvival battleArenaSurvival = new BattleArenaSurvival();
        
        // Configurar la batalla con Pokémon aleatorios
        battleArenaSurvival.setupSurvivalBattle(coachName1, coachName2);
        
        // Guardar la arena como la actual
        this.battleArenaNormal = battleArenaSurvival;
    }

    public void flee(){
        battleArenaNormal.flee();
    }
    
    public void useItem(String itemName) throws PoobkemonException {
        // Delegar la lógica del uso de ítem a la arena de batalla
        battleArenaNormal.useItem(itemName);
    }

    public void switchToPokemon(int index) throws PoobkemonException {
        System.out.println(1);
        // Delegar la lógica del cambio de Pokémon a la arena de batalla
        battleArenaNormal.switchToPokemon(index);
    }

    public BattleArena getBattleArena() {
        return battleArenaNormal;
    }

    public void statusEffect(){
        // Delegar la lógica del efecto de estado a la arena de batalla
        battleArenaNormal.statusEffect();
    }

}


