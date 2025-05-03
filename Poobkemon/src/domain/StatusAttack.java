package domain;

import java.util.ArrayList;
import java.util.List;


public class StatusAttack extends Attack {
    public static final List<StatusAttack> ataquesStatus = new ArrayList<>();
    private String affects;
    private int turnosDuracion; // Duración del estado (en turnos, -1 si es indefinido)
    static {
        // Movimientos de estado con "statAffected" y "turnosDuracion"
ataquesStatus.add(new StatusAttack("Defensa férrea", "Acero", 0, 5, 100, "PhysicalDefense", 3)); // Aumenta Defensa física (3 turnos)
ataquesStatus.add(new StatusAttack("Eco metálico", "Acero", 0, 5, 85, "SpecialAttack", 3));     // Aumenta Ataque Especial (3 turnos)
ataquesStatus.add(new StatusAttack("Hidrochorro", "Agua", 0, 5, 100, "Speed", 2));             // Reduce Velocidad del oponente (2 turnos)
ataquesStatus.add(new StatusAttack("Danza dragón", "Dragon", 0, 5, 100, "PhysicalAttack+Speed", 3)); // Aumenta Ataque y Velocidad (3 turnos)
ataquesStatus.add(new StatusAttack("Levitón", "Electrico", 0, 5, 100, "Evasion", 4));          // Aumenta Evasión (4 turnos)
ataquesStatus.add(new StatusAttack("Maldición", "Fantasma", 0, 5, 100, "PS", 3));             // Daño por turno (hasta que cambia Pokémon)
ataquesStatus.add(new StatusAttack("Pesadilla", "Fantasma", 0, 5, 100, "PS", 2));             // Daño si está dormido (hasta despertar)
ataquesStatus.add(new StatusAttack("Fuego fatuo", "Fuego", 0, 5, 85, "Burn", 5));             // Quema (hasta ser curada)
ataquesStatus.add(new StatusAttack("Danza flores", "Hada", 0, 5, 100, "SpecialAttack", 3));    // Aumenta Ataque Especial (3 turnos)
ataquesStatus.add(new StatusAttack("Neblina", "Hielo", 0, 5, 100, "SpecialDefense", 5));       // Aumenta Defensa Especial equipo (5 turnos)
ataquesStatus.add(new StatusAttack("Niebla", "Hielo", 0, 5, 100, "Evasion", 4));               // Reduce Evasión oponente (4 turnos)
ataquesStatus.add(new StatusAttack("Detección", "Lucha", 0, 5, 100, "Evasion", 3));            // Aumenta Evasión (3 turnos)
ataquesStatus.add(new StatusAttack("Corpulencia", "Lucha", 0, 5, 100, "PhysicalDefense", 3));  // Aumenta Defensa física (3 turnos)
ataquesStatus.add(new StatusAttack("Alivio", "Normal", 0, 5, 100, "Status", 0));               // Cura problemas de estado (instantáneo)
ataquesStatus.add(new StatusAttack("Reserva", "Normal", 0, 5, 100, "SpecialDefense", 4));      // Aumenta Defensa Especial (4 turnos)
ataquesStatus.add(new StatusAttack("Aromaterapia", "Planta", 0, 5, 100, "Status", 0));         // Cura problemas de estado equipo (instantáneo)
ataquesStatus.add(new StatusAttack("Silbato", "Planta", 0, 5, 55, "PhysicalAttack", 3));       // Reduce Ataque oponente (3 turnos)
ataquesStatus.add(new StatusAttack("Chapoteo lodo", "Tierra", 0, 5, 100, "Accuracy", 3));      // Reduce Precisión oponente (3 turnos)
ataquesStatus.add(new StatusAttack("Púas", "Tierra", 0, 5, 100, "PhysicalDefense", 4));        // Aumenta Defensa física (4 turnos)
ataquesStatus.add(new StatusAttack("Bilis", "Veneno", 0, 5, 100, "PS", 5));               // Envenena (hasta ser curado)
ataquesStatus.add(new StatusAttack("Viento afín", "Volador", 0, 5, 100, "Speed", 3));          // Aumenta Velocidad (3 turnos)
ataquesStatus.add(new StatusAttack("Respiro", "Volador", 0, 5, 100, "PS", 0));                 // Cura PS (instantáneo)

    }
    
    public StatusAttack(String name, String type, int damage, int powerPoints, int precision, String affects, int turnosDuracion) {
        super(name, type, damage, powerPoints, precision);
        this.affects = affects;
        this.turnosDuracion = turnosDuracion;
    }
    
    public String getAffects() {
        return affects;
    }

    public int getTurnosDuracion() {
        return turnosDuracion;
    }

    @Override
    public int calcDaño(Pokemon atacante, Pokemon defensor) {
        // No aplica daño directo, pero modifica atributos
        if (affects.equals("PhysicalDefense")) {
            defensor.setPhysicalDefense(defensor.getPhysicalDefense() + 10); // Ejemplo
        } else if (affects.equals("Speed")) {
            defensor.setSpeed(Math.max(defensor.getSpeed() - 10, 0)); // Ejemplo
        }
        usarAtaque();
        return 0;
    }
}