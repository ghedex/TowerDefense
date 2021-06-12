package enemy.magicianEnemy;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;
import levels.LevelTwo;

public class Magician extends Entity {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public Magician() {
        super(WIDTH, HEIGHT, 30, Assets.manager.get(Assets.magicianEnemy, TextureAtlas.class));
    }
}

