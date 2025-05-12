package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleArenaSurvival extends BattleArena {
    
    public BattleArenaSurvival() {
        super();
    }

    @Override
    public void attack(String moveName, String itself) throws PoobkemonException {
        Coach currentCoach = getCurrentCoach();
        Coach opponentCoach = getOpponentCoach();

        Pokemon attacker = currentCoach.getActivePokemon();
        Pokemon defender = opponentCoach.getActivePokemon();

        // Encuentra el ataque por nombre
        Attack attack = attacker.getAtaques().stream()
            .filter(a -> a.getName().equals(moveName))
            .findFirst()
            .orElse(null); // Si no encuentra el ataque, devuelve null

        if (attack.getPowerPoint() > 0){
            if ("itself".equals(itself)) {
            // Realiza el ataque sobre sí mismo
            
            attacker.attack(attacker, attack);
            } else {
            // Realiza el ataque sobre el oponente
            attacker.attack(defender, attack);
            }
        } else {
            System.out.println("No puedes usar este ataque, no tienes PP.");
        }
    }

    /**
     * Configura una batalla en modo supervivencia con Pokémon aleatorios.
     * @param coachName1 Nombre del primer entrenador
     * @param coachName2 Nombre del segundo entrenador
     * @throws PoobkemonException Si no hay suficientes Pokémon disponibles
     */
    public void setupSurvivalBattle(String coachName1, String coachName2) throws PoobkemonException {
        // Obtener todos los Pokémon disponibles
        List<String> allPokemon = Poobkemon.getAvailablePokemon();
        
        // Verificar que hay suficientes Pokémon
        if (allPokemon.size() < 12) {
            throw new PoobkemonException("No hay suficientes Pokémon disponibles para el modo Survival.");
        }
        
        // Mezclar la lista para obtener Pokémon aleatorios
        Collections.shuffle(allPokemon);
        
        // Seleccionar 6 Pokémon para cada jugador
        ArrayList<String> pokemons1 = new ArrayList<>(allPokemon.subList(0, 6));
        ArrayList<String> pokemons2 = new ArrayList<>(allPokemon.subList(6, 12));
        
        // Configurar los entrenadores con los Pokémon aleatorios
        setupCoaches(coachName1, coachName2, pokemons1, pokemons2, 
                    new ArrayList<>(), new ArrayList<>(), new String[6][4], new String[6][4]);
    }
}
