package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import enemy.AnimationEntity;
import enemy.Entity;
import levels.LevelOne;
import levels.PathfindingEnemy;


public class Scorpion extends Entity {

    public Scorpion() {
        super(90, 90, 100, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
    }
}