package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import enemy.AnimationEntity;
import levels.LevelOne;
import levels.PathfindingEnemy;


public class Scorpion extends Sprite {

    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    Scorpion scorpion;
    private float timePassed;
    private TextureAtlas scorpionAtlas;
    private Animation<TextureRegion> animation;
    TextureAtlas atlas;

    public final String PATH = "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";

    public Scorpion(){

        //scorpionEnemy.setPosition(-100, 150);



    }

}
