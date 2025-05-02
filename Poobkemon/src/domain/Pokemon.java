package domain;

import java.util.ArrayList;

public class Pokemon{
    private String name;
    private ArrayList<Effect> efectos = new ArrayList<>();{}
    private int id;
    private int ps;
    private int speed;
    private int underEffect = 0;
    private int specialAttack;
    private int physicalAttack;
    private int specialDefense;
    private int physicalDefense;
    private int power; //Precision of the attack
    private int evasion;
    private String type; //Type of the Pokemon (e.g., fire, water, etc.)
    //private String status; //Status of the Pokemon (e.g., poisoned, paralyzed, etc.)

    public Pokemon(String name, int id, int ps, int speed, int underEffect, int specialAttack, int physicalAttack, int specialDefense, int physicalDefense, int power, int evasion, String type) {
        this.name = name;
        this.id = id;
        this.ps = ps;
        this.speed = speed;
        this.underEffect = underEffect;
        this.specialAttack = specialAttack;
        this.physicalAttack = physicalAttack;
        this.specialDefense = specialDefense;
        this.physicalDefense = physicalDefense;
        this.power = power;
        this.evasion = evasion;
        this.type = type;
    }
}