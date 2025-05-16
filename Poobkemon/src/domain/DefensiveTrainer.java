package domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DefensiveTrainer extends Trainer {
    private Random random = new Random();

    public DefensiveTrainer(String name) {
        super(name, "Defensive");
    }

    @Override
    public String selectMove() {
        if (activePokemon == null || activePokemon.getAtaques().isEmpty()) {
            return null;
        }
        
        List<Attack> availableAttacks = activePokemon.getAtaques();
        
        // Primero busca ataques de estado que mejoren defensas
        Optional<Attack> defensiveStatusMove = availableAttacks.stream()
            .filter(attack -> attack instanceof StatusAttack)
            .filter(attack -> {
                StatusAttack statusAttack = (StatusAttack) attack;
                String effect = statusAttack.getEffect();
                return effect.contains("PhysicalDefense") || 
                       effect.contains("SpecialDefense") || 
                       effect.contains("Evasion");
            })
            .findFirst();
        
        if (defensiveStatusMove.isPresent()) {
            return defensiveStatusMove.get().getName();
        }
        
        // Luego busca ataques que bajen las estadísticas de ataque del oponente
        Optional<Attack> debuffAttackMove = availableAttacks.stream()
            .filter(attack -> attack instanceof StatusAttack)
            .filter(attack -> {
                StatusAttack statusAttack = (StatusAttack) attack;
                String effect = statusAttack.getEffect();
                return effect.contains("PhysicalAttack") || effect.contains("SpecialAttack");
            })
            .findFirst();
        
        if (debuffAttackMove.isPresent()) {
            return debuffAttackMove.get().getName();
        }
        
        // Si no hay ataques defensivos, selecciona el ataque más fuerte
        return availableAttacks.stream()
            .max(Comparator.comparing(Attack::getBaseDamage))
            .orElse(availableAttacks.get(random.nextInt(availableAttacks.size())))
            .getName();
    }
    
    @Override
    public int selectBestPokemon() {
        // Selecciona el Pokémon con mejores estadísticas defensivas
        int bestIndex = 0;
        int bestDefense = 0;
        
        for (int i = 0; i < pokemonTeam.size(); i++) {
            Pokemon pokemon = pokemonTeam.get(i);
            if (pokemon.getPs() <= 0) continue; // Ignora Pokémon debilitados
            
            int combinedDefense = pokemon.getPhysicalDefense() + pokemon.getSpecialDefense();
            if (combinedDefense > bestDefense) {
                bestDefense = combinedDefense;
                bestIndex = i;
            }
        }
        
        return bestIndex;
    }
}