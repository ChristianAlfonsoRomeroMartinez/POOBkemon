package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class AttackFactory {
    private static final Map<String, Attack> ATTACK_REGISTRY = new HashMap<>();

    static {
        // Registrar ataques físicos
        ATTACK_REGISTRY.put("Puño meteoro", new PhysicalAttack("Puño meteoro", "Acero", 90, 5, 90, "Physical"));
        ATTACK_REGISTRY.put("Ala de acero", new PhysicalAttack("Ala de acero", "Acero", 70, 5, 90, "Physical"));
        ATTACK_REGISTRY.put("Cascada", new PhysicalAttack("Cascada", "Agua", 80, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Matillazo", new PhysicalAttack("Matillazo", "Agua", 100, 5, 90, "Physical"));
        ATTACK_REGISTRY.put("Garra dragón", new PhysicalAttack("Garra dragón", "Dragon", 80, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Enfado", new PhysicalAttack("Enfado", "Dragon", 120, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Chispa", new PhysicalAttack("Chispa", "Electrico", 65, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Puño trueno", new PhysicalAttack("Puño trueno", "Electrico", 75, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Lengüetazo", new PhysicalAttack("Lengüetazo", "Fantasma", 30, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Impresionar", new PhysicalAttack("Impresionar", "Fantasma", 30, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Puño fuego", new PhysicalAttack("Puño fuego", "Fuego", 75, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Fuego sagrado", new PhysicalAttack("Fuego sagrado", "Fuego", 100, 5, 95, "Physical"));
        ATTACK_REGISTRY.put("Bola hielo", new PhysicalAttack("Bola hielo", "Hielo", 30, 5, 90, "Physical"));
        ATTACK_REGISTRY.put("Carámbano", new PhysicalAttack("Carámbano", "Hielo", 25, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Demolición", new PhysicalAttack("Demolición", "Lucha", 75, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Daño secreto", new PhysicalAttack("Daño secreto", "Normal", 70, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Esfuerzo", new PhysicalAttack("Esfuerzo", "Normal", 0, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Brazo pincho", new PhysicalAttack("Brazo pincho", "Planta", 60, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Hoja aguda", new PhysicalAttack("Hoja aguda", "Planta", 90, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Tumba rocas", new PhysicalAttack("Tumba rocas", "Roca", 60, 5, 95, "Physical"));
        ATTACK_REGISTRY.put("Pedrada", new PhysicalAttack("Pedrada", "Roca", 25, 5, 90, "Physical"));
        ATTACK_REGISTRY.put("Desarme", new PhysicalAttack("Desarme", "Siniestro", 65, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Finta", new PhysicalAttack("Finta", "Siniestro", 60, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Ataque óseo", new PhysicalAttack("Ataque óseo", "Tierra", 75, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Magnitud", new PhysicalAttack("Magnitud", "Tierra", 0, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Picotazo veneno", new PhysicalAttack("Picotazo veneno", "Veneno", 15, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Colmillo veneno", new PhysicalAttack("Colmillo veneno", "Veneno", 50, 5, 100, "Physical"));
        ATTACK_REGISTRY.put("Bote", new PhysicalAttack("Bote", "Volador", 85, 5, 85, "Physical"));
        ATTACK_REGISTRY.put("Colmillo aéreo", new PhysicalAttack("Colmillo aéreo", "Volador", 50, 5, 100, "Physical"));

        // Registrar ataques especiales
        ATTACK_REGISTRY.put("Deseo oculto", new SpecialAttack("Deseo oculto", "Acero", 80, 20, 90, "Special"));
        ATTACK_REGISTRY.put("Burbuja", new SpecialAttack("Burbuja", "Agua", 140, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Hidrobomba", new SpecialAttack("Hidrobomba", "Agua", 110, 20, 80, "Special"));
        ATTACK_REGISTRY.put("Furia dragón", new SpecialAttack("Furia dragón", "Dragon", 0, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Cometa draco", new SpecialAttack("Cometa draco", "Dragon", 130, 5, 90, "Special"));
        ATTACK_REGISTRY.put("Rayo carga", new SpecialAttack("Rayo carga", "Electrico", 50, 5, 90, "Special"));
        ATTACK_REGISTRY.put("Chispazo", new SpecialAttack("Chispazo", "Electrico", 80, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Viento aciago", new SpecialAttack("Viento aciago", "Fantasma", 60, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Bola sombra", new SpecialAttack("Bola sombra", "Fantasma", 80, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Anillo ígneo", new SpecialAttack("Anillo ígneo", "Fuego", 150, 5, 90, "Special"));
        ATTACK_REGISTRY.put("Sofoco", new SpecialAttack("Sofoco", "Fuego", 130, 5, 90, "Special"));
        ATTACK_REGISTRY.put("Frío polar", new SpecialAttack("Frío polar", "Hielo", 0, 5, 30, "Special"));
        ATTACK_REGISTRY.put("Nieve polvo", new SpecialAttack("Nieve polvo", "Hielo", 40, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Alboroto", new SpecialAttack("Alboroto", "Normal", 90, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Vozarrón", new SpecialAttack("Vozarrón", "Normal", 90, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Hoja mágica", new SpecialAttack("Hoja mágica", "Planta", 60, 5, 0, "Special"));
        ATTACK_REGISTRY.put("Silbato", new SpecialAttack("Silbato", "Planta", 0, 5, 55, "Special"));
        ATTACK_REGISTRY.put("Manto espejo", new SpecialAttack("Manto espejo", "Psiquico", 0, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Premonición", new SpecialAttack("Premonición", "Psiquico", 120, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Poder pasado", new SpecialAttack("Poder pasado", "Roca", 60, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Roca afilada", new SpecialAttack("Roca afilada", "Roca", 80, 5, 90, "Special"));
        ATTACK_REGISTRY.put("Disparo lodo", new SpecialAttack("Disparo lodo", "Tierra", 55, 5, 95, "Special"));
        ATTACK_REGISTRY.put("Bofetón lodo", new SpecialAttack("Bofetón lodo", "Tierra", 20, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Bomba lodo", new SpecialAttack("Bomba lodo", "Veneno", 90, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Ácido", new SpecialAttack("Ácido", "Veneno", 40, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Tornado", new SpecialAttack("Tornado", "Volador", 40, 5, 100, "Special"));
        ATTACK_REGISTRY.put("Aerochorro", new SpecialAttack("Aerochorro", "Volador", 100, 5, 95, "Special"));

        // Registrar ataques de estado
        ATTACK_REGISTRY.put("Defensa férrea", new StatusAttack("Defensa férrea", "Acero", 0, 5, 100, "PhysicalDefense", 3, "Status"));
        ATTACK_REGISTRY.put("Eco metálico", new StatusAttack("Eco metálico", "Acero", 0, 5, 85, "SpecialAttack", 3, "Status"));
        ATTACK_REGISTRY.put("Hidrochorro", new StatusAttack("Hidrochorro", "Agua", 0, 5, 100, "Speed", 2, "Status"));
        ATTACK_REGISTRY.put("Danza dragón", new StatusAttack("Danza dragón", "Dragon", 0, 5, 100, "PhysicalAttack+Speed", 3, "Status"));
        ATTACK_REGISTRY.put("Levitón", new StatusAttack("Levitón", "Electrico", 0, 5, 100, "Evasion", 4, "Status"));
        ATTACK_REGISTRY.put("Maldición", new StatusAttack("Maldición", "Fantasma", 0, 5, 100, "PS", 3, "Status"));
        ATTACK_REGISTRY.put("Pesadilla", new StatusAttack("Pesadilla", "Fantasma", 0, 5, 100, "PS", 2, "Status"));
        ATTACK_REGISTRY.put("Fuego fatuo", new StatusAttack("Fuego fatuo", "Fuego", 0, 5, 85, "Burn", 5, "Status"));
        ATTACK_REGISTRY.put("Danza flores", new StatusAttack("Danza flores", "Hada", 0, 5, 100, "SpecialAttack", 3, "Status"));
        ATTACK_REGISTRY.put("Neblina", new StatusAttack("Neblina", "Hielo", 0, 5, 100, "SpecialDefense", 5, "Status"));
        ATTACK_REGISTRY.put("Niebla", new StatusAttack("Niebla", "Hielo", 0, 5, 100, "Evasion", 4, "Status"));
        ATTACK_REGISTRY.put("Detección", new StatusAttack("Detección", "Lucha", 0, 5, 100, "Evasion", 3, "Status"));
        ATTACK_REGISTRY.put("Corpulencia", new StatusAttack("Corpulencia", "Lucha", 0, 5, 100, "PhysicalDefense", 3, "Status"));
        ATTACK_REGISTRY.put("Alivio", new StatusAttack("Alivio", "Normal", 0, 5, 100, "Status", 0, "Status"));
        ATTACK_REGISTRY.put("Reserva", new StatusAttack("Reserva", "Normal", 0, 5, 100, "SpecialDefense", 4, "Status"));
        ATTACK_REGISTRY.put("Aromaterapia", new StatusAttack("Aromaterapia", "Planta", 0, 5, 100, "Status", 0, "Status"));
        ATTACK_REGISTRY.put("Silbato", new StatusAttack("Silbato", "Planta", 0, 5, 55, "PhysicalAttack", 3, "Status"));
        ATTACK_REGISTRY.put("Chapoteo lodo", new StatusAttack("Chapoteo lodo", "Tierra", 0, 5, 100, "Accuracy", 3, "Status"));
        ATTACK_REGISTRY.put("Púas", new StatusAttack("Púas", "Tierra", 0, 5, 100, "PhysicalDefense", 4, "Status"));
        ATTACK_REGISTRY.put("Bilis", new StatusAttack("Bilis", "Veneno", 0, 5, 100, "PS", 5, "Status"));
        ATTACK_REGISTRY.put("Viento afín", new StatusAttack("Viento afín", "Volador", 0, 5, 100, "Speed", 3, "Status"));
        ATTACK_REGISTRY.put("Respiro", new StatusAttack("Respiro", "Volador", 0, 5, 100, "PS", 0, "Status"));
    }

    public static Attack createAttack(String name) {
        Attack attack = ATTACK_REGISTRY.get(name);
        if (attack == null) {
            throw new IllegalArgumentException("El ataque '" + name + "' no está registrado.");
        }
        return attack.clone();
    }

    public static List<String> getAttackNames() {
        return new ArrayList<>(ATTACK_REGISTRY.keySet());
    }

    /**
     * Devuelve todos los ataques registrados.
     * @return Un mapa con todos los ataques registrados.
     */
    public static Map<String, Attack> getAllAttacks() {
        return ATTACK_REGISTRY;
    }
}