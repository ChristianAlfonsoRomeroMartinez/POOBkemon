package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhysicalAttack extends Attack {
    public static final List<PhysicalAttack> ataquesFisicos = new ArrayList<>();
    static {
        
        ataquesFisicos.add(new PhysicalAttack("Puño meteoro", "Acero", 90, 5, 90));
        ataquesFisicos.add(new PhysicalAttack("Ala de acero", "Acero", 70, 5, 90));
        ataquesFisicos.add(new PhysicalAttack("Cascada", "Agua", 80, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Matillazo", "Agua", 100, 5, 90));
        ataquesFisicos.add(new PhysicalAttack("Garra dragón", "Dragon", 80, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Enfado", "Dragon", 120, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Chispa", "Electrico", 65, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Puño trueno", "Electrico", 75, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Lengüetazo", "Fantasma", 30, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Impresionar", "Fantasma", 30, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Puño fuego", "Fuego", 75, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Fuego sagrado", "Fuego", 100, 5, 95));
        ataquesFisicos.add(new PhysicalAttack("Bola hielo", "Hielo", 30, 5, 90));
        ataquesFisicos.add(new PhysicalAttack("Carámbano", "Hielo", 25, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Demolición", "Lucha", 75, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Daño secreto", "Normal", 70, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Esfuerzo", "Normal", 0, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("PBrazo pincho", "Planta", 60, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Hoja aguda", "Planta", 90, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Tumba rocas", "Roca", 60, 5, 95));
        ataquesFisicos.add(new PhysicalAttack("Pedrada", "Roca", 25, 5, 90 ));
        ataquesFisicos.add(new PhysicalAttack("Desarme", "Siniestro", 65, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Finta", "Siniestro", 60, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Ataque óseo", "Tierra", 75, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Magnitud", "Tierra", 0, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Picotazo veneno", "Veneno", 15, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Colmillo veneno", "Veneno", 50, 5, 100));
        ataquesFisicos.add(new PhysicalAttack("Bote", "Volador", 85, 5, 85));
        ataquesFisicos.add(new PhysicalAttack("Colmillo veneno", "Volador", 50, 5, 100));

        
    }
    
    
    public PhysicalAttack(String name, String type, int baseDamage, int powerPoint, int precision) {
        super(name, type, baseDamage, powerPoint, precision);
    }
    
    // En PhysicalAttack
    @Override
    public int calcDaño(Pokemon atacante, Pokemon defensor) {
        if (powerPoint <= 0) return 0;
        
        Random rand = new Random();
        if (rand.nextInt(100) + 1 > precision) return 0;
        
        // Usar número de tipo en lugar de string
        double efectividad = efectivity.efectividad(
            numberType.get(this.getType()), 
            numberType.get(defensor.getType())
        );
        
        int danioBase = (int) ((atacante.getPhysicalAttack() * baseDamage * efectividad) / defensor.getPhysicalDefense());
        danioBase = Math.max(danioBase, 1); // Mínimo 1 de daño
        
        usarAtaque();
        return danioBase;
    }
}