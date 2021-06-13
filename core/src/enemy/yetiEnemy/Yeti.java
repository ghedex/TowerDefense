package enemy.yetiEnemy;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;
import levels.LevelTwo;

    public class Yeti extends Entity {
        public static final int WIDTH = 50;
        public static final int HEIGHT = 50;

        public Yeti() {
            super(WIDTH, HEIGHT, 30, Assets.manager.get(Assets.yetiEnemy, TextureAtlas.class));
        }

    }

