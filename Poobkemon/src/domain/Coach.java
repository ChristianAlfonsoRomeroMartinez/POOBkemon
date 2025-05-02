package domain;

import java.util.ArrayList;
import java.util.HashMap;


public class Coach {
    private HashMap<String,Pokemon> pokemons = new HashMap <>();
    private ArrayList<Item> items = new ArrayList<>();
    private Pokemon currentPokemon;
    private int Score;

    public Coach(Pokemon currentPokemon) {
        this.currentPokemon = currentPokemon;
        this.Score = 0;
    }

    public void useItem(Item item, String type) {
        item.applyItemEffect(currentPokemon);

        items.remove(item);
        // Apply the effect of the item to the current Pokemon
        

    }

    public void attack( Pokemon opponentPokemon, Attack attack) {
        this.currentPokemon.attack(opponentPokemon);
    }

    public void changePokemon(String pokemonName) {
        Pokemon pokemon = pokemons.get(pokemonName);
        if (pokemon.ps > 0){ 
            this.currentPokemon = pokemon;
        }else{
            //PONER UNA EXCEPCION QUE DIGA QUE NO SE PUEDE CAMBIAR PORQUE NO TIENE VIDA
        }
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }

    public void runAway() {
        for(String key : pokemons.keySet()){
            Pokemon pokemon = pokemons.get(key);
            pokemon.setPs(0);
        }
    }

}