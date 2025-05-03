package domain;

public abstract class Attack {
    protected String name;
    protected String type;
    protected int baseDamage;
    protected int precision;
    protected int powerPoint;

    public Attack(String name, String type, int baseDamage, int powerPoint, int precision) {
        this.name = name;
        this.type = type;
        this.baseDamage = baseDamage;
        this.powerPoint = powerPoint;
        this.precision = precision;
    }

    public String getName() {
        return name;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public String getType() {
        return type;
    }

    public int getPrecision() {
        return precision;
    }

    public int getPowerPoint() {
        return powerPoint;
    }

    public void usarAtaque() {
        this.powerPoint = Math.max(this.powerPoint - 1, 0);
    }

    public abstract int calcularDanio(Pokemon atacante, Pokemon defensor);
}
