package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpecialAttack extends Attack {
    public static final List<SpecialAttack> ataquesEspeciales = new ArrayList<>();
    static {

        ataquesEspeciales.add(new SpecialAttack("Deseo oculto", "Acero", 80, 20, 90));
        ataquesEspeciales.add(new SpecialAttack("Burbuja", "Agua", 140, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Hidrobomba", "Agua", 110, 20, 80));
        ataquesEspeciales.add(new SpecialAttack("Furia dragón", "Dragon", 0, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Cometa draco", "Dragon", 130, 5, 90));
        ataquesEspeciales.add(new SpecialAttack("Rayo carga", "Electrico", 50, 5, 90));
        ataquesEspeciales.add(new SpecialAttack("Chispazo", "Electrico", 80, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Viento aciago", "Fantasma", 60, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Bola sombra", "Fantasma", 80, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Anillo ígneo", "Fuego", 150, 5, 90));
        ataquesEspeciales.add(new SpecialAttack("Sofoco", "Fuego", 130, 5, 90));
        ataquesEspeciales.add(new SpecialAttack("Frío polar", "Hielo", 0, 5, 30));
        ataquesEspeciales.add(new SpecialAttack("Nieve polvo", "Hielo", 40, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Alboroto", "Nomal", 90, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Vozarrón", "Normal", 90, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Hoja mágica", "Planta", 60, 5, 0));
        ataquesEspeciales.add(new SpecialAttack("Silbato", "Planta", 0, 5, 55));
        ataquesEspeciales.add(new SpecialAttack("Manto espejo", "Psiquico", 0, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Premonición", "Psiquico", 120, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Poder pasado", "Roca", 60, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Roca afilada", "Roca", 80, 5, 90));
        ataquesEspeciales.add(new SpecialAttack("Disparo lodo", "Tierra", 55, 5, 95));
        ataquesEspeciales.add(new SpecialAttack("Bofetón lodo", "Tierra", 20, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Bomba lodo", "Veneno", 90, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Ácido", "Veneno", 40, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Tornado", "Volador", 40, 5, 100));
        ataquesEspeciales.add(new SpecialAttack("Aerochorro", "Volador", 100, 5, 95));

    }
    
    public SpecialAttack(String name, String type, int baseDamage, int powerPoint, int precision) {
        super(name, type, baseDamage, powerPoint, precision);
    }
    
    @Override
    public int calcDaño(Pokemon atacante, Pokemon defensor) {
        if (powerPoint <= 0) return 0;
        
        Random rand = new Random();
        if (rand.nextInt(100) + 1 > precision) return 0;
        
        double efectividad = Efectividad.efectividad(numberType.get(this.getType()), 
                                                   numberType.get(defensor.getType()));
        int danioBase = (int) ((atacante.getSpecialAttack() * baseDamage * efectividad) / defensor.getSpecialDefense());
        danioBase = Math.max(danioBase, 1); // Mínimo 1 de daño
        
        usarAtaque();
        return danioBase;
    }
}