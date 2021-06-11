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
    private float fireCost = 50f;
    private float thunderCost = 500f;
    private float explosionCost = 1000f;
    private float timeCost = 2000f;




    public Ability() {
        super(WIDTH, HEIGHT, 1, "abilities/abilitesSkin/abilitiesSkin.atlas");
    }

    public float getFireDamage() {
        return fireDamage;
    }
    public void setFireDamage(float fireCost) {
        this.thunderCost = thunderCost;
    }
    public float getFireCost() {
        return fireCost;
    }
    public void setFireCost(float fireCost) {
        this.fireCost = fireCost;
    }

    public float getThunderDamage() { return thunderDamage; }
    public void setThunderDamage(float thunderDamage) {
        this.thunderDamage = thunderDamage;
    }
    public float getThunderCost() {
        return thunderCost;
    }
    public void setThunderCost(float thunderCost) {
        this.thunderCost = thunderCost;
    }

    public float getExplosionDamage() {
        return explosionDamage;
    }
    public void setExplosionDamage(float explosionDamage) {
        this.explosionDamage = explosionDamage;
    }
    public float getExplosionCost() { return explosionCost; }
    public void setExplosionCost(float explosionCost) {
        this.explosionCost = explosionCost;
    }

    public float getTimeDamage() {
        return timeDamage;
    }
    public void setTimeDamage(float timeDamage) {
        this.timeDamage = timeDamage;
    }
    public float getTimeCost() { return timeCost; }
    public void setTimeCost(float timeCost) {
        this.timeCost = timeCost;
    }

}