package domain;

public class Machine {
    private String name;
    private String type;

    public Machine(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String selectMove() {
        // Implementar lógica para seleccionar un movimiento
        return null;
    }
}
