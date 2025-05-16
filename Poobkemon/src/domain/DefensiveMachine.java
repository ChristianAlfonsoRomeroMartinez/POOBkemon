package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DefensiveMachine extends Machine {
    private Random random = new Random();

    public DefensiveMachine(String name, ArrayList<Pokemon> pokemons, ArrayList<String> items) {
        super(name, "Defensive", pokemons, items);
    }

    @Override
    public String selectMove() {
        if (getActivePokemon() == null || getActivePokemon().getAtaques().isEmpty()) {
            return null;
        }
        
        List<Attack> availableAttacks = getActivePokemon().getAtaques();
        
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
        
        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon pokemon = pokemons.get(i);
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