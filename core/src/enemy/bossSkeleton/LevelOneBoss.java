package enemy.bossSkeleton;

import enemy.Entity;
import levels.LevelOne;

public class LevelOneBoss extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public LevelOneBoss() {
        super(WIDTH, HEIGHT, 100, "core/assets/assetsPack/tree/treeBoss.atlas");
        super.setPosition(LevelOne.levelOneTopPath().first().x, LevelOne.levelOneTopPath().first().y);
    }

}