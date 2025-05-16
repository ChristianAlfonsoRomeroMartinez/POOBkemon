package model;

public class TrainerFactory {
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