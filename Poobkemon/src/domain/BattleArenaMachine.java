package domain;

import java.util.ArrayList;

/**
 * Arena de batalla especializada para manejar combates con entrenadores controlados por máquina
 */
public class BattleArenaMachine extends BattleArenaNormal {

    public BattleArenaMachine() {
        super();
    }

    /**
     * Configura una batalla entre un humano y una máquina (humano como jugador 1)
     */
    public void setupHumanVsMachine(String humanName, String machineName, 
                                   ArrayList<String> humanPokemon, ArrayList<String> machinePokemon,
                                   ArrayList<String> humanItems, String[][] humanAttacks, 
                                   String machineType) throws PoobkemonException {
        // Crear listas de Pokémon y ataques
        ArrayList<Pokemon> humanPokemonObjects = new ArrayList<>();
        for (String pokemonName : humanPokemon) {
            humanPokemonObjects.add(PokemonFactory.createPokemon(pokemonName));
        }
        
        // Configurar entrenador humano con constructor correcto
        HumanCoach humanCoach = new HumanCoach(humanName, humanPokemonObjects, humanItems);
        
        // Asignar ataques a los Pokémon del humano
        for (int i = 0; i < humanPokemon.size() && i < humanAttacks.length; i++) {
            Pokemon pokemon = humanPokemonObjects.get(i);
            for (int j = 0; j < humanAttacks[i].length; j++) {
                String attackName = humanAttacks[i][j];
                if (attackName != null && !attackName.isEmpty()) {
                    Attack attack = AttackFactory.createAttack(attackName);
                    pokemon.addAttack(attack);
                }
            }
        }
        
        // Configurar entrenador máquina según el tipo
        Machine machineCoach = createMachine(machineType, machineName, machinePokemon);
        
        // Usar setupCoaches para configurar correctamente la arena
        super.setupCoaches(humanName, machineName, 
                          humanPokemon, machinePokemon, 
                          humanItems, new ArrayList<>(), 
                          humanAttacks, new String[machinePokemon.size()][4]);
        
        // Si setupCoaches no está disponible o no es apropiado:
        // Sobrescribir la configuración manualmente si hay métodos protegidos disponibles
        // setPlayer1(humanCoach);
        // setPlayer2(machineCoach);
        
        // Configurar el oponente para la máquina si es necesario
        machineCoach.setOpponent(humanCoach);
    }

    /**
     * Configura una batalla entre una máquina y un humano (máquina como jugador 1)
     */
    public void setupMachineVsHuman(String machineName, String humanName, 
                                   ArrayList<String> machinePokemon, ArrayList<String> humanPokemon,
                                   ArrayList<String> humanItems, String[][] humanAttacks, 
                                   String machineType) throws PoobkemonException {
        // Crear listas de Pokémon y ataques
        ArrayList<Pokemon> humanPokemonObjects = new ArrayList<>();
        for (String pokemonName : humanPokemon) {
            humanPokemonObjects.add(PokemonFactory.createPokemon(pokemonName));
        }
        
        // Configurar entrenador humano con constructor correcto
        HumanCoach humanCoach = new HumanCoach(humanName, humanPokemonObjects, humanItems);
        
        // Asignar ataques a los Pokémon del humano
        for (int i = 0; i < humanPokemon.size() && i < humanAttacks.length; i++) {
            Pokemon pokemon = humanPokemonObjects.get(i);
            for (int j = 0; j < humanAttacks[i].length; j++) {
                String attackName = humanAttacks[i][j];
                if (attackName != null && !attackName.isEmpty()) {
                    Attack attack = AttackFactory.createAttack(attackName);
                    pokemon.addAttack(attack);
                }
            }
        }
        
        // Configurar entrenador máquina según el tipo
        Machine machineCoach = createMachine(machineType, machineName, machinePokemon);
        
        // Usar setupCoaches para configurar correctamente la arena
        super.setupCoaches(machineName, humanName, 
                          machinePokemon, humanPokemon, 
                          new ArrayList<>(), humanItems, 
                          new String[machinePokemon.size()][4], humanAttacks);
        
        // Configurar el oponente para la máquina si es necesario
        machineCoach.setOpponent(humanCoach);
    }

    /**
     * Configura una batalla entre dos máquinas
     */
    public void setupMachineVsMachine(String machine1Name, String machine2Name, 
                                     ArrayList<String> machine1Pokemon, ArrayList<String> machine2Pokemon,
                                     String machine1Type, String machine2Type) throws PoobkemonException {
        // Configurar primera máquina
        Machine machine1Coach = createMachine(machine1Type, machine1Name, machine1Pokemon);
        
        // Configurar segunda máquina
        Machine machine2Coach = createMachine(machine2Type, machine2Name, machine2Pokemon);
        
        // Usar setupCoaches para configurar correctamente la arena
        super.setupCoaches(machine1Name, machine2Name, 
                          machine1Pokemon, machine2Pokemon, 
                          new ArrayList<>(), new ArrayList<>(), 
                          new String[machine1Pokemon.size()][4], new String[machine2Pokemon.size()][4]);
        
        // Configurar oponentes para cada máquina
        machine1Coach.setOpponent(machine2Coach);
        machine2Coach.setOpponent(machine1Coach);
    }
    
    /**
     * Método auxiliar para crear una máquina según su tipo
     */
    private Machine createMachine(String machineType, String machineName, ArrayList<String> pokemonList) {
        ArrayList<Pokemon> pokemonObjects = new ArrayList<>();
        for (String pokemonName : pokemonList) {
            pokemonObjects.add(PokemonFactory.createPokemon(pokemonName));
        }
        
        // Crear máquina según el tipo especificado
        switch(machineType) {
            case "Defensive":
                return new DefensiveMachine(machineName, pokemonObjects, new ArrayList<>());
            case "Attacking":
                return new AttackingMachine(machineName, pokemonObjects, new ArrayList<>());
            case "Strategic":
                return new ExpertMachine(machineName, pokemonObjects, new ArrayList<>()); // Suponiendo que ExpertMachine implementa estrategia mixta
            case "Expert":
                return new ExpertMachine(machineName, pokemonObjects, new ArrayList<>());
            default:
                // Por defecto, usamos DefensiveMachine
                return new DefensiveMachine(machineName, pokemonObjects, new ArrayList<>());
        }
    }
}