package enemy.warriorEntity;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;


public class Warrior extends Entity {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public Warrior() {
        super(WIDTH, HEIGHT, 20, Assets.manager.get(Assets.warriorEnemy, TextureAtlas.class));
    }

}
