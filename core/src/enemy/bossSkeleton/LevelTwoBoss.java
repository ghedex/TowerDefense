package enemy.bossSkeleton;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;
import levels.LevelOne;
import levels.LevelTwo;

public class LevelTwoBoss extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public LevelTwoBoss() {
        super(WIDTH, HEIGHT, 100, Assets.manager.get(Assets.levelTwoBossCommander, TextureAtlas.class ));
        super.setPosition(LevelTwo.levelTwoTopPath().first().x, LevelTwo.levelTwoTopPath().first().y);
    }
}
