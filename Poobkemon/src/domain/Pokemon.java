package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    protected String name;
    protected int id;
    protected int total_ps;
    protected int ps;
    protected int speed;
    protected int specialAttack;
    protected int physicalAttack;
    protected int specialDefense;
    protected int physicalDefense;
    protected String type;
    protected int evasion;
    protected ArrayList<Attack> ataques = new ArrayList<>();

    public Pokemon(String name, int id, int ps, int speed, int specialAttack,
                   int physicalAttack, int specialDefense, int physicalDefense,
                   String type, int evasion) {
        this.name = name;
        this.id = id;
        this.total_ps = ps;
        this.ps = ps;
        this.speed = speed;
        this.specialAttack = specialAttack;
        this.physicalAttack = physicalAttack;
        this.specialDefense = specialDefense;
        this.physicalDefense = physicalDefense;
        this.type = type;
        this.evasion = evasion;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getId() { return id; }
    public int getTotalPs() { return total_ps; }
    public int getPs() { return ps; }
    public int getSpeed() { return speed; }
    public int getEvasion() { return evasion; }
    public int getSpecialAttack() { return specialAttack; }
    public int getPhysicalAttack() { return physicalAttack; }
    public int getSpecialDefense() { return specialDefense; }
    public int getPhysicalDefense() { return physicalDefense; }

    public void setSpeed(int speed) { this.speed = speed; }
    public void setPs(int ps) { this.ps = Math.max(ps, 0); }
    public void setEvasion(int evasion) { this.evasion = evasion; }
    public void setSpecialAttack(int specialAttack) { this.specialAttack = specialAttack; }
    public void setSpecialDefense(int specialDefense) { this.specialDefense = specialDefense; }
    public void setPhysicalAttack(int physicalAttack) { this.physicalAttack = physicalAttack; }
    public void setPhysicalDefense(int physicalDefense) { this.physicalDefense = physicalDefense; }

    public void setAtaques(List<Attack> ataques) {
        if (ataques.size() > 4) {
            throw new IllegalArgumentException("Un Pokémon solo puede tener hasta 4 ataques.");
        }
        this.ataques = new ArrayList<>(ataques);
    }

    public List<Attack> getAtaques() {
        return ataques;
    }

    /**
     * Ataca a otro Pokémon con un ataque específico.
     */
    public void attack(Pokemon defensor, Attack attack) {
        if (!ataques.contains(attack)) {
            throw new IllegalArgumentException("El Pokémon no conoce este ataque.");
        }
        int daño = attack.calcDaño(this, defensor);
        if (daño > 0) {
            defensor.setPs(defensor.getPs() - daño);
        }
    }

    /**
     * Realiza un ataque a sí mismo o al oponente.
     * @param toItself si true, se ataca a sí mismo.
     * @param attack el ataque a usar.
     */
    public void attack(boolean toItself, Attack attack) {
        if (!ataques.contains(attack)) {
            throw new IllegalArgumentException("El Pokémon no conoce este ataque.");
        }

        if (toItself) {
            attackItself(attack);
        } else {
            throw new UnsupportedOperationException("Debes usar attack(Pokemon, Attack) para atacar al oponente.");
        }
    }

    /**
     * Aplica un ataque a sí mismo (por ejemplo, por confusión).
     */
    private void attackItself(Attack attack) {
        int daño = attack.calcDaño(this, this); // Atacándose a sí mismo
        if (daño > 0) {
            setPs(ps - daño);
        }
    }
}
