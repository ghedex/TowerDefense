package abilities;

import MainRef.TowerDefense;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import enemy.Entity;

public class FireAbility extends Entity {
    Rectangle bounds;
    BodyDef bdef = new BodyDef();
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public FireAbility() {
        super(WIDTH, HEIGHT, 100, 1, "abilities/abilitesSkin/abilitiesSkin.atlas");
    }

    public Rectangle getBounds() {
        return bounds;
    }

    private void setXY(float pX, float pY) {
        setPosition(pX, pY);
        bounds.setX((int) pX);
        bounds.setY((int) pY);
    }
}
