package domain;

import java.util.ArrayList;

public class BattleArenaNormal extends BattleArena {

    public BattleArenaNormal() {
        super();
    }

    @Override
    public void startBattle(String coachName1, String coachName2, ArrayList<String> pokemons1,
                            ArrayList<String> pokemons2, ArrayList<String> items1, ArrayList<String> items2,
                            Attack[][] pokemAttacks1, Attack[][] pokemAttacks2) throws PoobkemonException {

        // Configurar entrenadores y sus recursos
        setupCoaches(coachName1, coachName2, pokemons1, pokemons2, items1, items2, pokemAttacks1, pokemAttacks2);

        // Ciclo principal de la batalla
        while (!isBattleFinished()) {
            try {
                // Iniciar turno
                //System.out.println("Turno de: " + getCurrentCoach().getName());
                startTurnTimer(getCurrentTurn());

                // Ejecutar acción del entrenador actual
                int action = getCurrentCoach().selectAction();
                getCurrentCoach().doAction(action);

                // Detener el temporizador si la acción se completó a tiempo
                cancelTurnTimer();

                // Verificar si el Pokémon del oponente está debilitado
                Coach opponentCoach = getOpponentCoach();
                if (opponentCoach.checkPokemonStatus(opponentCoach.getActivePokemon())) {
                    System.out.println("Pokémon debilitado: " + opponentCoach.getActivePokemon().getName());
                    if (opponentCoach.areAllPokemonFainted()) {
                        throw new PoobkemonException(PoobkemonException.POKEMON_GAME_OVER);
                    } else {
                        // Cambiar al siguiente Pokémon si es necesario
                        //opponentCoach.switchPokemon(opponentCoach.getNextAvailablePokemonIndex());
                    }
                }

                // Aplicar efectos de estado al Pokémon activo
                applyStatusEffects();

                // Cambiar de turno
                nextTurn();

            } catch (PoobkemonException e) {
                System.out.println("Error durante la batalla: " + e.getMessage());
                break;
            }
        }

        // Finalizar batalla y determinar ganador
        announceWinner();
        endBattle();
    }

    /**
     * Aplica los efectos de estado a los Pokémon activos de ambos entrenadores.
     */
    private void applyStatusEffects() {
        for (Coach coach : getCoaches()) {
            Pokemon activePokemon = coach.getActivePokemon();
            if (activePokemon.getStatus() != 0) {
                System.out.println("Pokémon " + activePokemon.getName() + " está afectado por su estado.");
                activePokemon.applyEffectDamage();
            }
        }
    }
}