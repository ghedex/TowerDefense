package enemy.bossSkeleton;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;
import levels.LevelOne;

public class LevelOneBoss extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public LevelOneBoss() {
        super(WIDTH, HEIGHT, 100, Assets.manager.get(Assets.levelOneBossWalk, TextureAtlas.class));
        super.setPosition(LevelOne.levelOneTopPath().first().x, LevelOne.levelOneTopPath().first().y);
    }
}
