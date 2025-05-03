package domain;

import java.util.ArrayList;
import java.util.List;

public class SpecialAttack extends Attack {
    public static final List<SpecialAttack> ataquesStatus = new ArrayList<>();
    static {
        ataquesStatus.add(new SpecialAttack("Defensa férrea", "Acero", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Eco metálico", "Acero", 0, 5, 85));
        ataquesStatus.add(new SpecialAttack("Hidrochorro", "Agua", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Danza lluvia", "Agua", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Danza dragón", "Dragon", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Levitón", "Electrico", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Maldición", "Fantasma", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Pesadilla", "Fantasma", 0, 5, 100));
        ataquesStatus.add(new SpecialAttack("Día soleado", "Fuego", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Fuego fatuo", "Fuego", 0, 5, 85));
        ataquesStatus.add(new SpecialAttack("Beso dulce", "Hada", 0, 5, 75));
        ataquesStatus.add(new SpecialAttack("Danza flores", "Hada", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Neblina", "Hielo", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Niebla", "Hielo", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Detección", "Lucha", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Corpulencia", "Lucha", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Alivio", "Normal", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Reserva", "Normal", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Aromaterapia", "Planta", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Silbato", "Planta", 0, 5, 55));
        ataquesStatus.add(new SpecialAttack("Imitación", "Psíquico", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Sellar", "Psíquico", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Tormenta de arena", "Roca", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Legado", "Siniestro", 0, 5, 100));
        ataquesStatus.add(new SpecialAttack("Tormento", "Siniestro", 0, 5, 100));
        ataquesStatus.add(new SpecialAttack("Chapoteo lodo", "Tierra", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Púas", "Tierra", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Bilis", "Veneno", 0, 5, 100));
        ataquesStatus.add(new SpecialAttack("Púas tóxicas", "Veneno", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Viento afín", "Volador", 0, 5, 0));
        ataquesStatus.add(new SpecialAttack("Respiro", "Volador", 0, 5, 0));


    }
    
    public SpecialAttack(String name, String type, int damage, int powerPoints, int precision) {
        super(name, type, damage, powerPoints, precision);
    }
    
    //@Override
    //public int calcularDanio(Pokemon atacante, Pokemon defensor) {
    //    if (getPowerPoints() <= 0) return 0;
        
    //    Random rand = new Random();
    //    if (rand.nextInt(100) + 1 > getPrecision()) return 0;
        
 
        
    //    usarAtaque();
    //    return danioBase;
    //}
}