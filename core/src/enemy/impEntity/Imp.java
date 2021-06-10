package enemy.impEntity;

import MainRef.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import enemy.Entity;


public class Imp extends Entity {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    public Imp() {
        super(WIDTH, HEIGHT, 30, Assets.manager.get(Assets.impEnemy, TextureAtlas.class));
    }

    //TO DO: Skorpion muss schon animiert sein, damit ich nur den Skorpion callen muss

}
