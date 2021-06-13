package enemy.bossSkeleton;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;
import levels.LevelThree;
import levels.LevelTwo;

public class LevelThreeBoss extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public LevelThreeBoss() {
        super(WIDTH, HEIGHT, 100, Assets.manager.get(Assets.yetiBossEnemy, TextureAtlas.class));
        super.setPosition(LevelThree.levelThreeLeftPath().first().x, LevelThree.levelThreeLeftPath().first().y);
    }
}
