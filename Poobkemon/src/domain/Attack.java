package domain;

import java.util.Random;

public abstract class Attack {
    private String name;
    private String type;
    private int damage;
    private int precision;
    private int powerPoint;

    public Attack(String name, String type, int damage, int powerPoint) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.powerPoint = powerPoint;
        this.precision = generatePrecision();
    }


    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }

     private int generatePrecision() {
        Random random = new Random();
        return  random.nextInt(101); 
    }

    public void  PowerDispon() {
        powerPoint =- 1;
    }

    

    
}
