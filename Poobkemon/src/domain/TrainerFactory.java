package domain;

public class TrainerFactory {
    /**
     * Crea un entrenador del tipo especificado
     * @param type Tipo de entrenador ("defensive" o "attacking")
     * @param name Nombre del entrenador
     * @return Una instancia del entrenador solicitado
     */
    public static Trainer createTrainer(String type, String name) {
        if (type.equalsIgnoreCase("defensive")) {
            return new DefensiveTrainer(name);
        } else if (type.equalsIgnoreCase("attacking")) {
            return new AttackingTrainer(name);
        } else {
            throw new IllegalArgumentException("Unknown trainer type: " + type);
        }
    }
}