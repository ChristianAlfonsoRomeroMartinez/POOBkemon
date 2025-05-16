package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HumanTrainer extends Trainer {
    private List<String> items;
    private Random random = new Random();

    public HumanTrainer(String name) {
        super(name, "Human");
        this.items = new ArrayList<>();
        this.pokemonTeam = new ArrayList<>();
    }

    public void addItem(String itemName) {
        items.add(itemName);
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String selectMove() {
        // En un entrenador humano, esta función no se usa directamente 
        // ya que la selección la hace el usuario a través de la interfaz
        if (activePokemon == null || activePokemon.getAtaques().isEmpty()) {
            return null;
        }
        // Por defecto, devuelve el primer ataque 
        // (esto solo se usaría en caso de necesitar una selección automática)
        return activePokemon.getAtaques().get(0).getName();
    }

    @Override
    public int selectBestPokemon() {
        // Similar al método anterior, en un entrenador humano 
        // la selección la hace el usuario a través de la interfaz
        // Devolvemos el primer Pokémon no debilitado
        for (int i = 0; i < pokemonTeam.size(); i++) {
            if (pokemonTeam.get(i).getPs() > 0) {
                return i;
            }
        }
        return 0; // Si todos están debilitados, devuelve el primero
    }
    
    public List<Pokemon> getPokemonTeam() {
        return pokemonTeam;
    }
}