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
    private float fireDamage = 20f;
    private float thunderDamage = 5f;


    public Ability() {
        super(WIDTH, HEIGHT, 100, 1, "abilities/abilitesSkin/abilitiesSkin.atlas");
    }

    public float getFireDamage() {
        return fireDamage;
    }

    public void setFireDamage(float fireDamage) {
        this.fireDamage = fireDamage;
    }

    public float getThunderDamage() {
        return thunderDamage;
    }

    public void setThunderDamage(float thunderDamage) {
        this.thunderDamage = thunderDamage;
    }
}
