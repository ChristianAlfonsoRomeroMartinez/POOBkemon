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
        ATTACK_REGISTRY.put("Deseo oculto", new SpecialAttack("Deseo oculto", "Acero", 80, 20, 90));
        ATTACK_REGISTRY.put("Burbuja", new SpecialAttack("Burbuja", "Agua", 140, 5, 100));
        ATTACK_REGISTRY.put("Hidrobomba", new SpecialAttack("Hidrobomba", "Agua", 110, 20, 80));
        ATTACK_REGISTRY.put("Furia dragón", new SpecialAttack("Furia dragón", "Dragon", 0, 5, 100));
        ATTACK_REGISTRY.put("Cometa draco", new SpecialAttack("Cometa draco", "Dragon", 130, 5, 90));
        ATTACK_REGISTRY.put("Rayo carga", new SpecialAttack("Rayo carga", "Electrico", 50, 5, 90));
        ATTACK_REGISTRY.put("Chispazo", new SpecialAttack("Chispazo", "Electrico", 80, 5, 100));
        ATTACK_REGISTRY.put("Viento aciago", new SpecialAttack("Viento aciago", "Fantasma", 60, 5, 100));
        ATTACK_REGISTRY.put("Bola sombra", new SpecialAttack("Bola sombra", "Fantasma", 80, 5, 100));
        ATTACK_REGISTRY.put("Anillo ígneo", new SpecialAttack("Anillo ígneo", "Fuego", 150, 5, 90));
        ATTACK_REGISTRY.put("Sofoco", new SpecialAttack("Sofoco", "Fuego", 130, 5, 90));
        ATTACK_REGISTRY.put("Frío polar", new SpecialAttack("Frío polar", "Hielo", 0, 5, 30));
        ATTACK_REGISTRY.put("Nieve polvo", new SpecialAttack("Nieve polvo", "Hielo", 40, 5, 100));
        ATTACK_REGISTRY.put("Alboroto", new SpecialAttack("Alboroto", "Normal", 90, 5, 100));
        ATTACK_REGISTRY.put("Vozarrón", new SpecialAttack("Vozarrón", "Normal", 90, 5, 100));
        ATTACK_REGISTRY.put("Hoja mágica", new SpecialAttack("Hoja mágica", "Planta", 60, 5, 0));
        ATTACK_REGISTRY.put("Silbato", new SpecialAttack("Silbato", "Planta", 0, 5, 55));
        ATTACK_REGISTRY.put("Manto espejo", new SpecialAttack("Manto espejo", "Psiquico", 0, 5, 100));
        ATTACK_REGISTRY.put("Premonición", new SpecialAttack("Premonición", "Psiquico", 120, 5, 100));
        ATTACK_REGISTRY.put("Poder pasado", new SpecialAttack("Poder pasado", "Roca", 60, 5, 100));
        ATTACK_REGISTRY.put("Roca afilada", new SpecialAttack("Roca afilada", "Roca", 80, 5, 90));
        ATTACK_REGISTRY.put("Disparo lodo", new SpecialAttack("Disparo lodo", "Tierra", 55, 5, 95));
        ATTACK_REGISTRY.put("Bofetón lodo", new SpecialAttack("Bofetón lodo", "Tierra", 20, 5, 100));
        ATTACK_REGISTRY.put("Bomba lodo", new SpecialAttack("Bomba lodo", "Veneno", 90, 5, 100));
        ATTACK_REGISTRY.put("Ácido", new SpecialAttack("Ácido", "Veneno", 40, 5, 100));
        ATTACK_REGISTRY.put("Tornado", new SpecialAttack("Tornado", "Volador", 40, 5, 100));
        ATTACK_REGISTRY.put("Aerochorro", new SpecialAttack("Aerochorro", "Volador", 100, 5, 95));

        // Registrar ataques de estado
        ATTACK_REGISTRY.put("Fuego fatuo", new StatusAttack("Fuego fatuo", "Fuego", 0, 5, 85, "Burn", 5));
        ATTACK_REGISTRY.put("Danza dragón", new StatusAttack("Danza dragón", "Dragon", 0, 5, 100, "PhysicalAttack+Speed", 3));
        ATTACK_REGISTRY.put("Defensa férrea", new StatusAttack("Defensa férrea", "Acero", 0, 5, 100, "PhysicalDefense", 3));
        ATTACK_REGISTRY.put("Eco metálico", new StatusAttack("Eco metálico", "Acero", 0, 5, 85, "SpecialAttack", 3));
        ATTACK_REGISTRY.put("Hidrochorro", new StatusAttack("Hidrochorro", "Agua", 0, 5, 100, "Speed", 2));
        ATTACK_REGISTRY.put("Viento afín", new StatusAttack("Viento afín", "Volador", 0, 5, 100, "Speed", 3));
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
}
