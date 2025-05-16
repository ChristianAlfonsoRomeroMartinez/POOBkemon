package domain;

import java.util.ArrayList;

public class MachineFactory {
    /**
     * Crea una máquina del tipo especificado
     * @param type Tipo de máquina ("defensive" o "attacking")
     * @param name Nombre de la máquina
     * @param pokemons Lista de Pokémon de la máquina
     * @param items Lista de ítems de la máquina
     * @return Una instancia de la máquina solicitada
     */
    public static Machine createMachine(String type, String name, ArrayList<Pokemon> pokemons, ArrayList<String> items) {
        if (type.equalsIgnoreCase("defensive") || type.equalsIgnoreCase("defensiveTrainer")) {
            return new DefensiveMachine(name, pokemons, items);
        } else if (type.equalsIgnoreCase("attacking") || type.equalsIgnoreCase("attackingTrainer")) {
            return new AttackingMachine(name, pokemons, items);
        } else {
            throw new IllegalArgumentException("Unknown machine type: " + type);
        }
    }
}