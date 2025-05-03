package domain;

public abstract class Pokemon {
    protected String name;
    protected int id;
    protected int ps;
    protected int speed;
    protected int underEffect = 0;
    protected int specialAttack;
    protected int physicalAttack;
    protected int specialDefense;
    protected int physicalDefense;
    protected int type; // Tipo del Pokémon (índice en la matriz de efectividad)

    public Pokemon(String name, int id, int ps, int speed, int underEffect, int specialAttack, int physicalAttack, int specialDefense, int physicalDefense, int type) {
        this.name = name;
        this.id = id;
        this.ps = ps;
        this.speed = speed;
        this.underEffect = underEffect;
        this.specialAttack = specialAttack;
        this.physicalAttack = physicalAttack;
        this.specialDefense = specialDefense;
        this.physicalDefense = physicalDefense;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getPhysicalAttack() {
        return physicalAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public void recibirDanio(int cantidad) {
        this.ps = Math.max(this.ps - cantidad, 0);
    }
}