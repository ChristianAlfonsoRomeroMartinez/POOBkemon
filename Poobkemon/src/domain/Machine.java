package domain;

import java.util.ArrayList;
import java.util.Random;

public abstract class Machine extends Coach {
    protected String machineName;
    protected String machineType;
    protected Random random = new Random();

    public Machine(String name, String type, ArrayList<Pokemon> pokemons, ArrayList<String> items) {
        super(pokemons, items);
        this.machineName = name;
        this.machineType = type;
    }

    @Override
    public void handleTurnTimeout() {
        // Implementación por defecto para las máquinas
        Pokemon active = getActivePokemon();
        if (active != null) {
            active.reducePP();
        }
    }

    /**
     * Selecciona un movimiento basado en la estrategia de la máquina
     * @return El nombre del movimiento seleccionado
     */
    public abstract String selectMove();
    
    /**
     * Evalúa los pokémon del equipo y selecciona el mejor para la batalla
     * según la estrategia de la máquina.
     * @return El índice del pokémon seleccionado
     */
    public abstract int selectBestPokemon();

    /**
     * Obtiene el nombre de la máquina
     * @return Nombre de la máquina
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * Obtiene el tipo de estrategia de la máquina
     * @return Tipo de la máquina
     */
    public String getMachineType() {
        return machineType;
    }
}
