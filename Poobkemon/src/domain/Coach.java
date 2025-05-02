package domain;

import java.util.ArrayList;
public class Coach {
    private Pokemon[6] pokemons = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Pokemon currentPokemon;
    private int Score;

    public Coach(Pokemon currentPokemon) {
        this.currentPokemon = currentPokemon;
        this.Score = 0;
    }

    public void useItem(Item item, String type) {
        items.remove(item);
        // Apply the effect of the item to the current Pokemon
        item.applyItemEffect(currentPokemon);

    }

    public void attack(Pokemon this.currentPokemon, Pokemon opponentPokemon, Attack attack) {
        currentPokemon.attack(opponentPokemon);
    }

    public void changePokemon(Pokemon pokemon) {
        if pokemon.ps>0{ 
            this.currentPokemon = newPokemon;
        }
    }

    public void runAway() {
        // Logic to run away from a battle
    }

}