package abilities;

import MainRef.TowerDefense;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import enemy.Entity;

public class Ability extends Entity {
    Rectangle bounds;
    BodyDef bdef = new BodyDef();
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    private float fireDamage = 5f;
    private float thunderDamage = 50f;
    private float explosionDamage = 100f;
    private float timeDamage = 400f;
    //abilitycost
    private int fireCost = 50;
    private int thunderCost = 500;
    private int explosionCost = 1000;
    private int timeCost = 1500;




    public Ability() {
        super(WIDTH, HEIGHT, 1, "abilities/abilitesSkin/abilitiesSkin.atlas");
    }

    public float getFireDamage() {
        return fireDamage;
    }
    public void setFireDamage(float fireDamage) {
        this.fireDamage = fireDamage;
    }
    public int getFireCost() {
        return fireCost;
    }
    public void setFireCost(int fireCost) {
        this.fireCost = fireCost;
    }

    public float getThunderDamage() { return thunderDamage; }
    public void setThunderDamage(float thunderDamage) {
        this.thunderDamage = thunderDamage;
    }
    public int getThunderCost() {
        return thunderCost;
    }
    public void setThunderCost(int thunderCost) {
        this.thunderCost = thunderCost;
    }

    public float getExplosionDamage() {
        return explosionDamage;
    }
    public void setExplosionDamage(float explosionDamage) {
        this.explosionDamage = explosionDamage;
    }
    public int getExplosionCost() { return explosionCost; }
    public void setExplosionCost(int explosionCost) {
        this.explosionCost = explosionCost;
    }

    public float getTimeDamage() {
        return timeDamage;
    }
    public void setTimeDamage(float timeDamage) {
        this.timeDamage = timeDamage;
    }
    public int getTimeCost() { return timeCost; }
    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

}