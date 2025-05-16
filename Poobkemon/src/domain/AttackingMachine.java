package domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AttackingMachine extends Machine {
    private Random random = new Random();
    
    public AttackingTrainer(String name) {
        super(name, "Attacking");
    }

    @Override
    public String selectMove() {
        if (activePokemon == null || activePokemon.getAtaques().isEmpty()) {
            return null;
        }
        
        List<Attack> availableAttacks = activePokemon.getAtaques();
        
        // Primero busca ataques de estado que mejoren ataques
        Optional<Attack> offensiveStatusMove = availableAttacks.stream()
            .filter(attack -> attack instanceof StatusAttack)
            .filter(attack -> {
                StatusAttack statusAttack = (StatusAttack) attack;
                String effect = statusAttack.getEffect();
                return effect.contains("PhysicalAttack") || 
                       effect.contains("SpecialAttack") || 
                       effect.contains("Speed");
            })
            .findFirst();
        
        if (offensiveStatusMove.isPresent()) {
            return offensiveStatusMove.get().getName();
        }
        
        // Luego busca ataques que bajen las estadísticas defensivas del oponente
        Optional<Attack> debuffDefenseMove = availableAttacks.stream()
            .filter(attack -> attack instanceof StatusAttack)
            .filter(attack -> {
                StatusAttack statusAttack = (StatusAttack) attack;
                String effect = statusAttack.getEffect();
                return effect.contains("PhysicalDefense") || effect.contains("SpecialDefense");
            })
            .findFirst();
        
        if (debuffDefenseMove.isPresent()) {
            return debuffDefenseMove.get().getName();
        }
        
        // Si no hay ataques ofensivos de estado, selecciona el ataque más fuerte
        return availableAttacks.stream()
            .max(Comparator.comparing(Attack::getBaseDamage))
            .orElse(availableAttacks.get(random.nextInt(availableAttacks.size())))
            .getName();
    }
    
    @Override
    public int selectBestPokemon() {
        // Selecciona el Pokémon con mejores estadísticas ofensivas
        int bestIndex = 0;
        int bestAttack = 0;
        
        for (int i = 0; i < pokemonTeam.size(); i++) {
            Pokemon pokemon = pokemonTeam.get(i);
            if (pokemon.getPs() <= 0) continue; // Ignora Pokémon debilitados
            
            int combinedAttack = pokemon.getPhysicalAttack() + pokemon.getSpecialAttack();
            if (combinedAttack > bestAttack) {
                bestAttack = combinedAttack;
                bestIndex = i;
            }
        }
        
        return bestIndex;
    }
}