package domain;

import java.util.Arrays;
import java.util.List;

public class Poobkemon {
    private BattleArena battleArena;


    public static List<String> getAvailablePokemon() {
        // Implementación que retorna la lista de nombres
        return Arrays.asList("Charizard", "Blastoise", "Venusaur","Gengar","Dragonite","Togetic","Tyranitar","Gardevoir", "Snorlax","Metagross","Donphan", "Machamp","Delibird", "Raichu" );
    }
    
    // Métodos necesarios en el dominio
    public static List<String> getAvailableItems() {
        // Retorna lista de ítems disponibles
        return Arrays.asList("Poción", "Superpoción", "Elixir");
    }
    
    public static List<String> getAvailableAttacks() {
        // Lógica para obtener ataques según Pokémon
        return Arrays.asList("Ataque 1", "Ataque 2", "Ataque especial");
    }



    public void attack(String moveName){

    }

    /**
     * Inicia una nueva batalla entre dos entrenadores.
     * @param coachName1 Nombre del primer entrenador.
     * @param coachName2 Nombre del segundo entrenador.
     * @throws PoobkemonException Si ocurre un error al configurar la batalla.
     */
    public void startBattle(String coachName1, String coachName2) throws PoobkemonException {
        battleArena = new BattleArena(false); // Crear la arena de batalla
        battleArena.startBattlePvP(coachName1, coachName2);
    }

    /**
     * Ejecuta una acción seleccionada por el usuario.
     * @param action La acción seleccionada (ATTACK, USE_ITEM, SWITCH_POKEMON, RUN).
     * @throws PoobkemonException Si ocurre un error durante la ejecución de la acción.
     */
    public void performAction(BattleAction action) throws PoobkemonException {
        if (battleArena == null) {
            throw new PoobkemonException("No hay una batalla en curso.");
        }
        battleArena.handleAction(action, battleArena.getCurrentCoach(), battleArena.getOpponentCoach());
    }

    /**
     * Obtiene el estado actual de la batalla.
     * Este método puede ser usado por la GUI para actualizar la interfaz.
     * @return Una descripción del estado actual de la batalla.
     */
    public String getBattleState() {
        if (battleArena == null) {
            return "No hay una batalla en curso.";
        }
        return battleArena.getBattleState();
    }

    /**
     * Cambia al siguiente Pokémon disponible del entrenador actual.
     * @throws PoobkemonException Si no hay más Pokémon disponibles.
     */
    public void switchToNextPokemon() throws PoobkemonException {
        if (battleArena == null) {
            throw new PoobkemonException("No hay una batalla en curso.");
        }
        battleArena.getCurrentCoach().switchToNextPokemon();
    }

    /**
     * Usa un ítem en el Pokémon activo del entrenador actual.
     * @param item El ítem a usar.
     * @throws PoobkemonException Si ocurre un error al usar el ítem.
     */
    public void useItem(Item item) throws PoobkemonException {
        if (battleArena == null) {
            throw new PoobkemonException("No hay una batalla en curso.");
        }
        battleArena.getCurrentCoach().useItem(item);
    }

    /**
     * Finaliza la batalla actual.
     */
    public void endBattle() {
        if (battleArena != null) {
            battleArena.endBattle();
            battleArena = null;
        }
    }
}


