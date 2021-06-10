package enemy.wizardEntity;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import components.Lifebar;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;

public class Wizard extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    //Lifebar lifebar = new Lifebar();


    public Wizard() {
        super(WIDTH, HEIGHT, 400, Assets.manager.get(Assets.wizardEnemy, TextureAtlas.class));
    }



}
