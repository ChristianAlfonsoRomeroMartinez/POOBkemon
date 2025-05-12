package domain;

import java.util.HashMap;
import java.util.Map;

public class AttackFactory {
    private static final Map<String, Attack> ATTACK_REGISTRY = new HashMap<>();

    static {
        // Registrar ataques físicos
        ATTACK_REGISTRY.put("Puño meteoro", new PhysicalAttack("Puño meteoro", "Acero", 90, 5, 90));
        ATTACK_REGISTRY.put("Cascada", new PhysicalAttack("Cascada", "Agua", 80, 5, 100));
        ATTACK_REGISTRY.put("Garra dragón", new PhysicalAttack("Garra dragón", "Dragon", 80, 5, 100));
        ATTACK_REGISTRY.put("Chispa", new PhysicalAttack("Chispa", "Electrico", 65, 5, 100));
        ATTACK_REGISTRY.put("Puño trueno", new PhysicalAttack("Puño trueno", "Electrico", 75, 5, 100));
        ATTACK_REGISTRY.put("Lengüetazo", new PhysicalAttack("Lengüetazo", "Fantasma", 30, 5, 100));

        // Registrar ataques especiales
        ATTACK_REGISTRY.put("Hidrobomba", new SpecialAttack("Hidrobomba", "Agua", 110, 5, 80));
        ATTACK_REGISTRY.put("Bola sombra", new SpecialAttack("Bola sombra", "Fantasma", 80, 5, 100));
        ATTACK_REGISTRY.put("Rayo carga", new SpecialAttack("Rayo carga", "Electrico", 50, 5, 90));
        ATTACK_REGISTRY.put("Furia dragón", new SpecialAttack("Furia dragón", "Dragon", 0, 5, 100));
        ATTACK_REGISTRY.put("Cometa draco", new SpecialAttack("Cometa draco", "Dragon", 130, 5, 90));
        ATTACK_REGISTRY.put("Burbuja", new SpecialAttack("Burbuja", "Agua", 140, 5, 100));

        // Registrar ataques de estado
        ATTACK_REGISTRY.put("Fuego fatuo", new StatusAttack("Fuego fatuo", "Fuego", 0, 5, 85, "Burn", 5));
        ATTACK_REGISTRY.put("Danza dragón", new StatusAttack("Danza dragón", "Dragon", 0, 5, 100, "PhysicalAttack+Speed", 3));
        ATTACK_REGISTRY.put("Defensa férrea", new StatusAttack("Defensa férrea", "Acero", 0, 5, 100, "PhysicalDefense", 3));
        ATTACK_REGISTRY.put("Eco metálico", new StatusAttack("Eco metálico", "Acero", 0, 5, 85, "SpecialAttack", 3));
        ATTACK_REGISTRY.put("Hidrochorro", new StatusAttack("Hidrochorro", "Agua", 0, 5, 100, "Speed", 2));
        ATTACK_REGISTRY.put("Viento afín", new StatusAttack("Viento afín", "Volador", 0, 5, 100, "Speed", 3));
    }

    /**
     * Crea un ataque basado en su nombre.
     * 
     * @param name El nombre del ataque.
     * @return Una nueva instancia del ataque.
     * @throws IllegalArgumentException Si el ataque no está registrado.
     */
    public static Attack createAttack(String name) {
        Attack attack = ATTACK_REGISTRY.get(name);
        if (attack == null) {
            throw new IllegalArgumentException("El ataque '" + name + "' no está registrado.");
        }
        return attack.clone(); // Clonar para evitar compartir la misma instancia
    }

    /**
     * Obtiene los nombres de todos los ataques registrados.
     * 
     * @return Una lista con los nombres de los ataques.
     */
    public static List<String> getAttackNames() {
        return new ArrayList<>(ATTACK_REGISTRY.keySet());
    }
}
