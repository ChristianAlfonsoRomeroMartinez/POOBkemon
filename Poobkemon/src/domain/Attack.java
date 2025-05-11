package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Attack {
    protected String name;
    protected String type;
    protected int baseDamage;
    protected int precision;
    protected int powerPoint;
    protected static HashMap<String, Integer> numberType = new HashMap<>(); // Efectos del ataque (ej. "paralizado", "quemado", etc.)
    static {
        numberType.put("Acero", 0);
        numberType.put("Agua", 1);
        numberType.put("Bicho", 2);
        numberType.put("Dragon", 3);
        numberType.put("Electrico", 4);
        numberType.put("Fantasma", 5);
        numberType.put("Fuego", 6);
        numberType.put("Hada", 7);
        numberType.put("Hielo", 8);
        numberType.put("Lucha", 9);
        numberType.put("Normal", 10);
        numberType.put("Planta", 11);
        numberType.put("Psíquico", 12);
        numberType.put("Roca", 13);
        numberType.put("Siniestro", 14);
        numberType.put("Tierra", 15);
        numberType.put("Veneno", 16);
        numberType.put("Volador", 17);
    }

    protected static ArrayList<String> attackTypes = new ArrayList<>(); // Lista de ataques que puede usar el Pokémon
    // 0 PyhsicalAttack, 1 SpecialAttack, 2 StatusAttack
    static {
        attackTypes.add("PhysicalAttack");
        attackTypes.add("SpecialAttack");
        attackTypes.add("StatusAttack");
    }

    public Attack(String name, String type, int baseDamage, int powerPoint, int precision) {
        this.name = name;
        this.type = type;
        this.baseDamage = baseDamage;
        this.powerPoint = powerPoint;
        this.precision = precision;
    }

    public String getName() {
        return name;
    }

    
    public int getBaseDamage() {
        return baseDamage;
    }

    public String getType() {
        return type;
    }

    public int getPrecision() {
        return precision;
    }
    
    public void setPowerPoint(int pp) {
    	this.powerPoint = pp;
    }

    public int getPowerPoint() {
        return powerPoint;
    }

    public void usarAtaque() {
        this.powerPoint = Math.max(this.powerPoint - 1, 0);
    }

    public int getAttackType() {
        return numberType.get(type);
    }


    public int calcDaño(Pokemon atacante, Pokemon defensor) {
        if (powerPoint <= 0) return 0;

        Random rand = new Random();
        if (rand.nextInt(100) + 1 > precision) {
            System.out.println("El ataque falló debido a la precisión.");
            return 0;
        }

        double efectividad = efectivity.efectividad(numberType.get(this.getType()), 
                                                   numberType.get(defensor.getType()));
        int danioBase = (int) ((atacante.getSpecialAttack() * baseDamage * efectividad) / defensor.getSpecialDefense());
        danioBase = Math.max(danioBase, 1); // Mínimo 1 de daño

        System.out.println("Efectividad: " + efectividad + ", Daño calculado: " + danioBase);
        usarAtaque();
        return danioBase;
    }
}
