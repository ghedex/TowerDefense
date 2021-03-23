package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import enemy.AnimationEntity;
import levels.LevelOne;
import levels.PathfindingEnemy;


public class Scorpion extends Actor {

    AnimationEntity ae;

    public final String PATH = "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";

    public Scorpion(){



        this.setSize(90, 90);
    }

    public Object returnPath(){
        return Gdx.files.internal(this.PATH);
    }



}
