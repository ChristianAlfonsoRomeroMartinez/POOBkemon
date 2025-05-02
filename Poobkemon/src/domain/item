package domain;

public class Item{
    private String name;
    private String description;
    private int effectValue;
    private String type; // Type of item (e.g., healing, attack boost, etc.)

    public item(String name, String description, int effectValue) {
        this.name = name;
        this.description = description;
        this.effectValue = effectValue;
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

    public void applyItemEffect(Pokemon pokemon, String type) {
        switch (type) {
            case "healing":
            pokemon.setHealth(pokemon.getHealth() + this.effectValue);
                break;
            case "attack":
                // Implement attack boost logic here
                break;
            case "defense":
                // Implement defense boost logic here
                break;
            default:
                System.out.println("Unknown item type.");
        }
        
    }
}