package abilities;

import enemy.Entity;

public class Ability extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    private int fireDamage = 5;
    private int thunderDamage = 50;
    private int explosionDamage = 100;
    private int timeDamage = 400;
    //abilitycost
    private int fireCost = 50;
    private int thunderCost = 500;
    private int explosionCost = 1000;
    private int timeCost = 1500;




    public Ability() {
        super(WIDTH, HEIGHT, 1, "abilities/abilitesSkin/abilitiesSkin.atlas");
    }

    public int getFireDamage() {
        return fireDamage;
    }

    public void setFireDamage(int fireDMG) {
        this.fireDamage += fireDMG;

    }
    public int getFireCost() {
        return fireCost;
    }
    public void setFireCost(int fireCost) {
        this.fireCost = fireCost;
    }

    public int getThunderDamage() { return thunderDamage; }
    public void setThunderDamage(int thunderDamage) {
        this.thunderDamage = thunderDamage;
    }
    public int getThunderCost() {
        return thunderCost;
    }
    public void setThunderCost(int thunderCost) {
        this.thunderCost = thunderCost;
    }

    public int getExplosionDamage() {
        return explosionDamage;
    }
    public void setExplosionDamage(int explosionDamage) {
        this.explosionDamage = explosionDamage;
    }
    public int getExplosionCost() { return explosionCost; }
    public void setExplosionCost(int explosionCost) {
        this.explosionCost = explosionCost;
    }

    public int getTimeDamage() {
        return timeDamage;
    }
    public void setTimeDamage(int timeDamage) {
        this.timeDamage = timeDamage;
    }
    public int getTimeCost() { return timeCost; }
    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

}