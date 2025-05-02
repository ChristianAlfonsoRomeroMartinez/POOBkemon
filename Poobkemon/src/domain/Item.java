package domain;

public class Item{
    private String name;
    private String description;
    private int effectValue;
    private int applyTo; // 0: vida, 1: ataque, 2: defensa, 3: ataqueEspecial, 4: defensaEspecial, 5: velocidad, 6: precision, 7: evasión


    public Item(String name, String description, int effectValue, int applyTo) {
        this.name = name;
        this.description = description;
        this.effectValue = effectValue;
        this.applyTo = applyTo;

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getEffectValue() {
        return this.effectValue;
    }   

    public void applyItemEffect(Pokemon pokemon ) {
        // vida ataque defensa ataqueEspecial defensaEspecial velocidad precision evasión
        pokemon.applyItemEffect(this.effectValue, this.applyTo);
    }
}