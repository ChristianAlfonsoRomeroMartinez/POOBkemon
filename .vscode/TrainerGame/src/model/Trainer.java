package model;

public abstract class Trainer {
    protected String name;
    protected String type;

    public Trainer(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public abstract void selectMove();

    public abstract void applyEffect();
}